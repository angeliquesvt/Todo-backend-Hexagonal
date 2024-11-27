package com.angelique.todo.domain.port.in;

import com.angelique.todo.domain.exception.TodoNotFoundException;
import com.angelique.todo.infrastructure.client.rest.model.TodoCreationDto;
import com.angelique.todo.infrastructure.client.rest.model.TodoDto;
import com.angelique.todo.infrastructure.client.rest.model.TodoUpdateDto;

import java.util.List;
import java.util.UUID;

public interface TodoServicePort {
    List<TodoDto> processGetTodos();

    TodoDto processGetTodo(UUID id) throws TodoNotFoundException;

    void processDeleteTodos(Boolean deleteCompleted);

    void processDeleteTodo(UUID id);

    TodoDto processCreateTodo(TodoCreationDto todoCreationDto);

    TodoDto processUpdateTodo(UUID id, TodoUpdateDto todoCreationDto);
}
