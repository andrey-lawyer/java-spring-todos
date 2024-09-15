package com.todos.service;

import com.todos.entity.TodoEntity;
import com.todos.entity.UserEntity;
import com.todos.exception.NotFoundException;
import com.todos.model.Todo;
import com.todos.repository.TodoRepository;
import com.todos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {
	
	@Autowired
	private TodoRepository todoRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	// Common method to find the logged-in user by email
	private UserEntity getUserByEmail(String email) throws NotFoundException {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("User with email " + email + " not found"));
	}
	
	// Common method to find a Todo by ID and check ownership
	private TodoEntity getTodoByIdAndCheckOwnership(Long id, String email) throws NotFoundException {
		UserEntity user = getUserByEmail(email);
		TodoEntity todo = todoRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Todo with id " + id + " not found"));
		
		if (!todo.getUser().getId().equals(user.getId())) {
			throw new NotFoundException("This Todo does not belong to the logged-in user");
		}
		
		return todo;
	}
	
	public Todo createTodoForLoggedInUser(TodoEntity todo, String email) throws NotFoundException {
		UserEntity user = getUserByEmail(email);
		todo.setUser(user);
		return Todo.toModel(todoRepository.save(todo));
	}
	
	public List<Todo> getAllTodoFromUser(String email) throws NotFoundException {
		getUserByEmail(email);
		List<TodoEntity> todoEntities = todoRepository.findByUserEmail(email);
		return todoEntities.stream()
				.map(Todo::toModel)
				.collect(Collectors.toList());
	}
	
	public Todo getTodoByIdForLoggedInUser(Long id, String email) throws NotFoundException {
		TodoEntity todo = getTodoByIdAndCheckOwnership(id, email);
		return Todo.toModel(todo);
	}
	
	public void deleteTodoByIdForLoggedInUser(Long id, String email) throws NotFoundException {
		TodoEntity todo = getTodoByIdAndCheckOwnership(id, email);
		todoRepository.delete(todo);
	}
	
	public Todo updateTodoForLoggedInUser(Long id, TodoEntity updatedTodo, String email) throws NotFoundException {
		TodoEntity existingTodo = getTodoByIdAndCheckOwnership(id, email);
		
		// Update properties of the task
		existingTodo.setTitle(updatedTodo.getTitle());
		existingTodo.setDescription(updatedTodo.getDescription());
		existingTodo.setCompleted(updatedTodo.getCompleted());
		
		return Todo.toModel(todoRepository.save(existingTodo));
	}
	
	// Mark task as complete or incomplete for the logged-in user
	public Todo completeTodoForLoggedInUser(Long id, String email) throws NotFoundException {
		TodoEntity todo = getTodoByIdAndCheckOwnership(id, email);
		todo.setCompleted(!todo.getCompleted());
		return Todo.toModel(todoRepository.save(todo));
	}
}