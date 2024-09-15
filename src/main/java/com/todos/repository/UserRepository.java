package com.todos.repository;

import com.todos.entity.UserEntity;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

//public interface UserRepository extends JpaRepository<UserEntity, Long>
public interface UserRepository extends CrudRepository<UserEntity, Long> {
	Optional<UserEntity> findByEmail(String userEmail);
}
