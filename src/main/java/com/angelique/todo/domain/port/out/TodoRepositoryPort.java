package com.angelique.todo.domain.port.out;

import com.angelique.todo.domain.model.Todo;
import com.angelique.todo.domain.model.TodoCreation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TodoRepositoryPort {
    List<Todo> findTodos();

    Optional<Todo> findTodoById(UUID id);

    Optional<Todo> findTodoByTitle(String title);

    void deleteTodos();

    void deleteTodo(UUID id);

    void deleteCompletedTodos();

    Optional<Todo> save(TodoCreation todo);

    Optional<Todo> update(Todo todo);
}
