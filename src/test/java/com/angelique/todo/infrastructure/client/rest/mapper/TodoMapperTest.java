package com.angelique.todo.infrastructure.client.rest.mapper;

import com.angelique.todo.domain.model.Todo;
import com.angelique.todo.infrastructure.client.rest.model.TodoDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TodoMapperTest {

    private static final PodamFactory factory = new PodamFactoryImpl();

    private final TodoMapper todoMapper = new TodoMapper();

    @Test
    @DisplayName("Should convert Todo to TodoDto")
    void toDto() {
        var todo = factory.manufacturePojo(Todo.class);
        var url = "http://test.com";
        var expectedResult = new TodoDto(todo.id(), todo.title(), todo.isCompleted(), todo.order(), url);

        var result = todoMapper.toDto(todo, url);

        assertEquals(expectedResult, result);
    }
}