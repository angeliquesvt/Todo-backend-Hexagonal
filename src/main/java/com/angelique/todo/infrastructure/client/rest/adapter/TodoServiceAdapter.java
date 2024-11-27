package com.angelique.todo.infrastructure.client.rest.adapter;

import com.angelique.todo.common.annotation.WebAdapterComponent;
import com.angelique.todo.domain.exception.TodoNotFoundException;
import com.angelique.todo.domain.model.Todo;
import com.angelique.todo.domain.model.TodoCreation;
import com.angelique.todo.domain.port.in.TodoServicePort;
import com.angelique.todo.domain.service.TodoService;
import com.angelique.todo.infrastructure.client.rest.mapper.TodoMapper;
import com.angelique.todo.infrastructure.client.rest.model.TodoCreationDto;
import com.angelique.todo.infrastructure.client.rest.model.TodoDto;
import com.angelique.todo.infrastructure.client.rest.model.TodoUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@WebAdapterComponent
@RequiredArgsConstructor
public class TodoServiceAdapter implements TodoServicePort {

    private final TodoService todoService;
    private final TodoMapper todoMapper;

    String formatTodoGetUrl(String todoId) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .pathSegment("todos", "{id}")
                .buildAndExpand(todoId)
                .toUriString();
    }

    @Override
    public List<TodoDto> processGetTodos() {
        List<Todo> todos = Optional.ofNullable(todoService.getTodos()).orElse(Collections.emptyList());
        return todos.stream()
                .map(todo -> todoMapper.toDto(todo, formatTodoGetUrl(todo.id().toString())))
                .toList();
    }

    @Override
    public TodoDto processGetTodo(UUID id) throws TodoNotFoundException {
        return Optional.ofNullable(todoService.getTodo(id))
                .map(todo -> todoMapper.toDto(todo, formatTodoGetUrl(todo.id().toString()))
                ).orElseThrow();
    }

    @Override
    public void processDeleteTodos(Boolean completed) {
        todoService.deleteTodos(completed);
    }

    @Override
    public void processDeleteTodo(UUID id) {
        todoService.deleteTodo(id);
    }

    @Override
    public TodoDto processCreateTodo(TodoCreationDto todoCreationDto) {
        TodoCreation todoCreation = new TodoCreation(todoCreationDto.title());
        return Optional.ofNullable(todoService.createTodo(todoCreation))
                .map(todo -> todoMapper.toDto(todo, formatTodoGetUrl(todo.id().toString())))
                .orElseThrow();
    }

    @Override
    public TodoDto processUpdateTodo(UUID id, TodoUpdateDto todoUpdateDto) {
        Todo updatedTodo = new Todo(id, todoUpdateDto.getTitle(), todoUpdateDto.getCompleted(), todoUpdateDto.getOrder());
        return Optional.ofNullable(todoService.updateTodo(updatedTodo))
                .map(todo -> todoMapper.toDto(todo, formatTodoGetUrl(todo.id().toString())))
                .orElseThrow();
    }
}
