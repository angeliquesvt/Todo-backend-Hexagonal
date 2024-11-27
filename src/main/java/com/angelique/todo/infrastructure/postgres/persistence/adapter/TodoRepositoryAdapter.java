package com.angelique.todo.infrastructure.postgres.persistence.adapter;

import com.angelique.todo.common.annotation.RepositoryAdapterComponent;
import com.angelique.todo.domain.model.Todo;
import com.angelique.todo.domain.model.TodoCreation;
import com.angelique.todo.domain.port.out.TodoRepositoryPort;
import com.angelique.todo.infrastructure.postgres.persistence.mapper.TodoEntityMapper;
import com.angelique.todo.infrastructure.postgres.persistence.model.TodoEntity;
import com.angelique.todo.infrastructure.postgres.persistence.repository.TodoRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RepositoryAdapterComponent
@AllArgsConstructor
public class TodoRepositoryAdapter implements TodoRepositoryPort {

    private final TodoRepository todoRepository;
    private final TodoEntityMapper todoEntityMapper;

    @Override
    public List<Todo> findTodos() {
        List<TodoEntity> todoEntity = Optional.of(todoRepository.findAllByOrderByOrderAsc())
                .orElse(List.of());

        return todoEntity.stream()
                .map(todoEntityMapper::toTodo)
                .toList();
    }

    @Override
    public Optional<Todo> findTodoById(UUID id) {
        return todoRepository.findById(id)
                .map(todoEntityMapper::toTodo);
    }

    @Override
    public Optional<Todo> findTodoByTitle(String title) {
        return todoRepository.findByTitle(title)
                .map(todoEntityMapper::toTodo);
    }

    @Override
    public void deleteTodos() {
        todoRepository.deleteAll();
    }

    @Override
    public void deleteCompletedTodos() {
        todoRepository.deleteByIsCompleted(true);
    }

    @Override
    public void deleteTodo(UUID id) {
        todoRepository.deleteById(id);
    }

    @Override
    public Optional<Todo> save(TodoCreation todo) {
        TodoEntity todoToSave = new TodoEntity(todo.title(), false, generateTodoOrder());
        return Optional.of(todoRepository.save(todoToSave))
                .map(todoEntityMapper::toTodo);
    }

    @Override
    public Optional<Todo> update(Todo todo) {
        TodoEntity todoEntityToSave = new TodoEntity(todo.id(), todo.title(), todo.isCompleted(), todo.order());
        return Optional.of(todoRepository.save(todoEntityToSave))
                .map(todoEntityMapper::toTodo);
    }

    Integer generateTodoOrder() {
        Optional<Integer> todoLastOrder = todoRepository.getMaxOrder();
        return todoLastOrder.map(lastOrder -> lastOrder + 1).orElse(1);
    }
}
