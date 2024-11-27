package com.angelique.todo.domain.service;

import com.angelique.todo.domain.exception.DuplicateTitleException;
import com.angelique.todo.domain.exception.TodoNotFoundException;
import com.angelique.todo.domain.model.Todo;
import com.angelique.todo.domain.model.TodoCreation;

import java.util.List;
import java.util.UUID;

public interface TodoService {
    Todo getTodo(UUID id) throws TodoNotFoundException;

    List<Todo> getTodos();

    void deleteTodos(Boolean isCompleted);

    Todo createTodo(TodoCreation todoCreation) throws DuplicateTitleException;

    Todo updateTodo(Todo todo) throws TodoNotFoundException, DuplicateTitleException;

    void deleteTodo(UUID id);
}
