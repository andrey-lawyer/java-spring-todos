package com.todos.repository;

import com.todos.entity.TodoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TodoRepository extends CrudRepository<TodoEntity, Long> {
	List<TodoEntity> findByUserEmail(String email);
}
