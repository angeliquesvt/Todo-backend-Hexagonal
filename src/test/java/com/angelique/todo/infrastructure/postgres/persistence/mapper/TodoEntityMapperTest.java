package com.angelique.todo.infrastructure.postgres.persistence.mapper;

import com.angelique.todo.domain.model.Todo;
import com.angelique.todo.infrastructure.postgres.persistence.model.TodoEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TodoEntityMapperTest {

    private static final PodamFactory factory = new PodamFactoryImpl();

    private final TodoEntityMapper todoMapper = new TodoEntityMapper();

    @Test
    @DisplayName("Should convert TodoEntity to Todo")
    void toTodo() {
        var todoEntity = factory.manufacturePojo(TodoEntity.class);
        var expectedResult = new Todo(
                todoEntity.getId(),
                todoEntity.getTitle(),
                todoEntity.getIsCompleted(),
                todoEntity.getOrder()
        );

        var result = todoMapper.toTodo(todoEntity);

        assertEquals(expectedResult, result);
    }
}