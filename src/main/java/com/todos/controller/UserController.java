package com.todos.controller;

import com.todos.dto.LoginResponseDTO;
import com.todos.dto.UserLoginDTO;
import com.todos.dto.UserRegistrationDTO;
import com.todos.entity.UserEntity;
import com.todos.exception.AuthenticationException;
import com.todos.model.User;

import com.todos.exception.NotFoundException;
import com.todos.exception.UserAlreadyExistException;
import com.todos.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")


public class UserController {
 
    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<User> signUp(@Valid @RequestBody UserRegistrationDTO registrationDTO) throws UserAlreadyExistException {
        User savedUser = userService.registerUser(registrationDTO);
        return ResponseEntity.ok(savedUser);
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody UserLoginDTO loginDTO) throws AuthenticationException {
        LoginResponseDTO loginResponseDTO = userService.loginUser(loginDTO);
        return ResponseEntity.ok(loginResponseDTO);
    }
    
    @GetMapping
    public ResponseEntity< List<User>> getAllUsers() {
        List<User> users =  userService.getAllUsers();
        return ResponseEntity.ok(users);
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws NotFoundException {
    User user = userService.getUserById(id);
    
    return ResponseEntity.ok(user);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserEntity user) throws NotFoundException {
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws NotFoundException {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
       
    }


}