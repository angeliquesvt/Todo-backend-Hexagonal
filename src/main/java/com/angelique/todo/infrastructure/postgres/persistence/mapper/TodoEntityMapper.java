package com.angelique.todo.infrastructure.postgres.persistence.mapper;

import com.angelique.todo.domain.model.Todo;
import com.angelique.todo.infrastructure.postgres.persistence.model.TodoEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TodoEntityMapper {

    public Todo toTodo(TodoEntity todoEntity) {
        return new Todo(
                todoEntity.getId(),
                todoEntity.getTitle(),
                todoEntity.getIsCompleted(),
                todoEntity.getOrder()
        );
    }
}
