package com.todos.controller;

import com.todos.config.JwtTokenProvider;
import com.todos.entity.TodoEntity;
import com.todos.exception.AuthenticationException;
import com.todos.exception.NotFoundException;
import com.todos.model.Todo;
import com.todos.service.TodoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    
    @Autowired
    private TodoService todoService;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    // Common method to extract user email from token
    private String getEmailFromRequest(HttpServletRequest request) throws AuthenticationException {
        String token = jwtTokenProvider.resolveToken(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            return jwtTokenProvider.getUsername(token);
        }
        throw new AuthenticationException("Invalid or missing token");
    }
    
    @PostMapping()
    public ResponseEntity<Todo> createTodo(@RequestBody TodoEntity todo, HttpServletRequest request) throws NotFoundException, AuthenticationException {
        String email = getEmailFromRequest(request);
        return ResponseEntity.ok(todoService.createTodoForLoggedInUser(todo, email));
    }
    
    @GetMapping()
    public ResponseEntity<List<Todo>> getAllTodo(HttpServletRequest request) throws NotFoundException, AuthenticationException {
        String email = getEmailFromRequest(request);
        return ResponseEntity.ok(todoService.getAllTodoFromUser(email));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id, HttpServletRequest request) throws NotFoundException, AuthenticationException {
        String email = getEmailFromRequest(request);
        return ResponseEntity.ok(todoService.getTodoByIdForLoggedInUser(id, email));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody TodoEntity todoEntity, HttpServletRequest request) throws NotFoundException, AuthenticationException {
        String email = getEmailFromRequest(request);
        return ResponseEntity.ok(todoService.updateTodoForLoggedInUser(id, todoEntity, email));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable Long id, HttpServletRequest request) throws NotFoundException, AuthenticationException {
        String email = getEmailFromRequest(request);
        todoService.deleteTodoByIdForLoggedInUser(id, email);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}/complete")
    public ResponseEntity<Todo> completeTodo(@PathVariable Long id, HttpServletRequest request) throws NotFoundException, AuthenticationException {
        String email = getEmailFromRequest(request);
        return ResponseEntity.ok(todoService.completeTodoForLoggedInUser(id, email));
    }
}