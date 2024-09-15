package com.todos.model;
import com.todos.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class User {
	private Long id;
	private String name;
	private String email;
	private List<Todo> todos;
	
	public static User toModel(UserEntity userEntity) {
		User modelUser = new User();
		modelUser.setId(userEntity.getId());
		modelUser.setEmail(userEntity.getEmail());
        modelUser.setName(userEntity.getName());
		List<Todo> todoModels = userEntity.getTodos().stream()
				.map(Todo::toModel)
				.collect(Collectors.toList());
		modelUser.setTodos(todoModels);
		return modelUser;
	}
	
}
