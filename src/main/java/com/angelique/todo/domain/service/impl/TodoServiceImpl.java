package com.angelique.todo.domain.service.impl;

import com.angelique.todo.domain.exception.DuplicateTitleException;
import com.angelique.todo.domain.exception.TodoNotFoundException;
import com.angelique.todo.domain.model.Todo;
import com.angelique.todo.domain.model.TodoCreation;
import com.angelique.todo.domain.port.out.TodoRepositoryPort;
import com.angelique.todo.domain.service.TodoService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final TodoRepositoryPort todoRepositoryPort;

    @Override
    public Todo getTodo(UUID id) {
        return todoRepositoryPort.findTodoById(id).orElseThrow(() -> new TodoNotFoundException(String.format("Todo not found with id: %s", id.toString())));
    }

    @Override
    public List<Todo> getTodos() {
        return todoRepositoryPort.findTodos();
    }

    @Override
    public void deleteTodos(Boolean isCompleted) {
        if (isCompleted != null && isCompleted) {
            todoRepositoryPort.deleteCompletedTodos();
        } else {
            todoRepositoryPort.deleteTodos();
        }
    }

    @Override
    public Todo createTodo(TodoCreation todoCreation) {

        verifyTitleDuplication(todoCreation.title());
        return todoRepositoryPort.save(todoCreation)
                .orElseThrow();
    }

    void verifyTitleDuplication(String title) {
        todoRepositoryPort.findTodoByTitle(title).ifPresent(todo -> {
            throw new DuplicateTitleException(String.format("Todo already exists with title: %s", title));
        });
    }

    @Override
    public Todo updateTodo(Todo todo) {
        getTodo(todo.id());
        verifyTitleDuplication(todo.title());
        return todoRepositoryPort.update(todo).orElseThrow();
    }

    @Override
    public void deleteTodo(UUID id) {
        getTodo(id);
        todoRepositoryPort.deleteTodo(id);
    }
}
