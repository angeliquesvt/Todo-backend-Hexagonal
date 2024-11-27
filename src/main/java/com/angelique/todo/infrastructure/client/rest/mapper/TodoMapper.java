package com.angelique.todo.infrastructure.client.rest.mapper;

import com.angelique.todo.domain.model.Todo;
import com.angelique.todo.infrastructure.client.rest.model.TodoDto;
import org.springframework.stereotype.Component;

@Component
public class TodoMapper {

    public TodoDto toDto(Todo todo, String url) {
        return new TodoDto(todo.id(), todo.title(), todo.isCompleted(), todo.order(), url);
    }
}
