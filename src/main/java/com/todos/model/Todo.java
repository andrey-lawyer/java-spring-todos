package com.todos.model;


import com.todos.entity.TodoEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Todo {
	private Long id;
	private String title;
	private String description;
	private Boolean completed;
	
	public static Todo toModel(TodoEntity entity) {
		Todo model = new Todo();
		model.setId(entity.getId());
		model.setCompleted(entity.getCompleted());
		model.setTitle(entity.getTitle());
		model.setDescription(entity.getDescription());
		return model;
	}
	
}
	

