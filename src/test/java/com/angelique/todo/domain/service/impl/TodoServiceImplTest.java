package com.angelique.todo.domain.service.impl;

import com.angelique.todo.domain.exception.DuplicateTitleException;
import com.angelique.todo.domain.exception.TodoNotFoundException;
import com.angelique.todo.domain.model.Todo;
import com.angelique.todo.domain.model.TodoCreation;
import com.angelique.todo.domain.port.out.TodoRepositoryPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TodoServiceImplTest {

    private final static PodamFactory factory = new PodamFactoryImpl();

    @Mock
    private TodoRepositoryPort todoRepositoryPort;

    @Spy
    @InjectMocks
    private TodoServiceImpl todoServiceImpl;

    @AfterEach
    void after() {
        verifyNoMoreInteractions(todoRepositoryPort);
    }

    @Test
    @DisplayName("Should get a todo")
    void getTodo() throws TodoNotFoundException {
        var todo = factory.manufacturePojo(Todo.class);
        var id = UUID.randomUUID();

        when(todoRepositoryPort.findTodoById(id)).thenReturn(Optional.ofNullable(todo));

        var result = todoServiceImpl.getTodo(id);

        verify(todoRepositoryPort).findTodoById(id);
        assertEquals(todo, result);
    }

    @Test
    @DisplayName("Should throw todo not found exception")
    void getTodo_notFoundException() {
        var id = UUID.randomUUID();

        when(todoRepositoryPort.findTodoById(id)).thenReturn(Optional.empty());


        assertThrows(TodoNotFoundException.class, () -> todoServiceImpl.getTodo(id));

        verify(todoRepositoryPort).findTodoById(id);
    }

    @Test
    @DisplayName("Should get all todos")
    void getTodos() {
        var todo = factory.manufacturePojo(Todo.class);
        var expectedResult = List.of(todo);

        when(todoRepositoryPort.findTodos()).thenReturn(expectedResult);

        var result = todoServiceImpl.getTodos();

        verify(todoRepositoryPort).findTodos();
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Should delete all todos")
    void deleteTodos() {
        doNothing().when(todoRepositoryPort).deleteTodos();

        todoServiceImpl.deleteTodos(false);

        verify(todoRepositoryPort).deleteTodos();
    }

    @Test
    @DisplayName("Should delete completed todos")
    void deleteCompletedTodos() {
        doNothing().when(todoRepositoryPort).deleteCompletedTodos();

        todoServiceImpl.deleteTodos(true);

        verify(todoRepositoryPort).deleteCompletedTodos();
    }

    @Test
    @DisplayName("Should update a todo")
    void updateTodo() {
        var todo = factory.manufacturePojo(Todo.class);
        var expectedResult = factory.manufacturePojo(Todo.class);

        doReturn(todo).when(todoServiceImpl).getTodo(todo.id());
        doNothing().when(todoServiceImpl).verifyTitleDuplication(todo.title());
        when(todoRepositoryPort.update(todo)).thenReturn(Optional.ofNullable(expectedResult));

        var result = todoServiceImpl.updateTodo(todo);

        verify(todoServiceImpl).getTodo(todo.id());
        verify(todoServiceImpl).verifyTitleDuplication(todo.title());
        verify(todoRepositoryPort).update(todo);
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Should create a todo")
    void createTodo() {
        var todoCreation = factory.manufacturePojo(TodoCreation.class);
        var expectedResult = factory.manufacturePojo(Todo.class);

        when(todoRepositoryPort.findTodoByTitle(todoCreation.title())).thenReturn(Optional.empty());
        when(todoRepositoryPort.save(todoCreation)).thenReturn(Optional.of(expectedResult));

        var result = todoServiceImpl.createTodo(todoCreation);

        verify(todoRepositoryPort).findTodoByTitle(todoCreation.title());
        verify(todoRepositoryPort).save(todoCreation);
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Should not throw exception when title is unique")
    void verifyTitleDuplication_uniqueTitle() {
        var uniqueTitle = "Unique Title";

        when(todoRepositoryPort.findTodoByTitle(uniqueTitle)).thenReturn(Optional.empty());

        todoServiceImpl.verifyTitleDuplication(uniqueTitle);

        verify(todoRepositoryPort).findTodoByTitle(uniqueTitle);
    }

    @Test
    @DisplayName("Should throw DuplicateTitleException when title already exists")
    void verifyTitleDuplication_duplicateTitle() {
        String duplicateTitle = "Duplicate Title";
        var existingTodo = factory.manufacturePojo(Todo.class);

        when(todoRepositoryPort.findTodoByTitle(duplicateTitle)).thenReturn(Optional.of(existingTodo));

        assertThrows(DuplicateTitleException.class, () -> todoServiceImpl.verifyTitleDuplication(duplicateTitle));

        verify(todoRepositoryPort).findTodoByTitle(duplicateTitle);
    }

    @Test
    @DisplayName("Should delete a todo by ID")
    void deleteTodo() {
        var id = UUID.randomUUID();
        var todo = factory.manufacturePojo(Todo.class);

        doReturn(todo).when(todoServiceImpl).getTodo(id);
        doNothing().when(todoRepositoryPort).deleteTodo(id);

        todoServiceImpl.deleteTodo(id);

        verify(todoServiceImpl).getTodo(id);
        verify(todoRepositoryPort).deleteTodo(id);
    }
}