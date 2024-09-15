package com.todos.service;

import com.todos.config.JwtTokenProvider;
import com.todos.dto.LoginResponseDTO;
import com.todos.dto.UserLoginDTO;
import com.todos.dto.UserRegistrationDTO;
import com.todos.entity.UserEntity;
import com.todos.exception.AuthenticationException;
import com.todos.exception.NotFoundException;
import com.todos.exception.UserAlreadyExistException;
import com.todos.model.User;
import com.todos.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;



@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
//    authorisation !!!
public User registerUser(UserRegistrationDTO registrationDTO) {
    // Check if there is a user with this email address
    userRepository.findByEmail(registrationDTO.getEmail())
            .ifPresent(existingUser -> {
	            try {
		            throw new UserAlreadyExistException("User with email '" + registrationDTO.getEmail() + "' already exists");
	            } catch (UserAlreadyExistException e) {
		            throw new RuntimeException(e);
	            }
            });
    
    //  Create a new user
    UserEntity userEntity = new UserEntity();
    userEntity.setName(registrationDTO.getName());
    userEntity.setEmail(registrationDTO.getEmail());
    userEntity.setPassword(passwordEncoder.encode(registrationDTO.getPassword())); // Encode password
    
    // Save the user in the database
    UserEntity savedUser = userRepository.save(userEntity);
    
    // Returning the user model
    return User.toModel(savedUser);
}
    
    public LoginResponseDTO loginUser(UserLoginDTO loginDTO) throws AuthenticationException {
        UserEntity userEntity = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new AuthenticationException("Invalid email or password"));
        
        // Password verification
        if (!passwordEncoder.matches(loginDTO.getPassword(), userEntity.getPassword())) {
            throw new AuthenticationException("Invalid email or password");
        }
        
        // Token generation
        String token = jwtTokenProvider.createToken(loginDTO.getEmail());
        return new LoginResponseDTO(token);
    }
    
    public List<User> getAllUsers() {
        
        List<UserEntity> userEntities = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .toList();
        
        // Convert each UserEntity into a User model
        return userEntities.stream()
                .map(User::toModel)
                .collect(Collectors.toList());
    }

    public User getUserById(Long id) throws NotFoundException {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
        return User.toModel(userEntity);
    }
    
    public User updateUser(Long id, UserEntity user) throws NotFoundException {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User with id " + id + " not found");
        }
        user.setId(id);
       UserEntity newUser =  userRepository.save(user);
       return User.toModel(newUser);

    }
    
    public void deleteUser(Long id) throws NotFoundException {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User with id " + id + " not found");
        }
        userRepository.deleteById(id);
    }
    
   
}
