package com.angelique.todo.infrastructure.client.rest.adapter;

import com.angelique.todo.domain.model.Todo;
import com.angelique.todo.domain.model.TodoCreation;
import com.angelique.todo.domain.service.TodoService;
import com.angelique.todo.infrastructure.client.rest.mapper.TodoMapper;
import com.angelique.todo.infrastructure.client.rest.model.TodoCreationDto;
import com.angelique.todo.infrastructure.client.rest.model.TodoDto;
import com.angelique.todo.infrastructure.client.rest.model.TodoUpdateDto;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceAdapterTest {

    private final static PodamFactory factory = new PodamFactoryImpl();
    @Mock
    private TodoService todoService;
    @Mock
    private TodoMapper todoMapper;
    @Spy
    @InjectMocks
    private TodoServiceAdapter todoServiceAdapter;

    @AfterEach
    void after() {
        verifyNoMoreInteractions(todoService, todoMapper);
    }

    @Test
    @DisplayName("processGetTodos should return a list of TodoDto")
    void processGetTodos() {
        var todo = factory.manufacturePojo(Todo.class);
        String url = "url";


        var todoDto = new TodoDto(todo.id(), todo.title(), todo.isCompleted(), todo.order(), url);

        var expectedResult = List.of(todoDto);
        doReturn(url).when(todoServiceAdapter).formatTodoGetUrl(todo.id().toString());
        when(todoService.getTodos()).thenReturn(List.of(todo));
        when(todoMapper.toDto(todo, url)).thenReturn(todoDto);

        var result = todoServiceAdapter.processGetTodos();

        verify(todoService).getTodos();
        verify(todoMapper).toDto(todo, url);
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("processGetTodo should return a TodoDto")
    void processGetTodo() {
        var todo = factory.manufacturePojo(Todo.class);
        var expectedResult = factory.manufacturePojo(TodoDto.class);
        UUID id = UUID.randomUUID();
        String url = "url";

        doReturn(url).when(todoServiceAdapter).formatTodoGetUrl(todo.id().toString());
        when(todoService.getTodo(id)).thenReturn(todo);
        when(todoMapper.toDto(todo, url)).thenReturn(expectedResult);

        var result = todoServiceAdapter.processGetTodo(id);

        verify(todoService).getTodo(id);
        verify(todoMapper).toDto(todo, url);
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("processDeleteTodos should call deleteTodos")
    void processDeleteTodos() {
        doNothing().when(todoService).deleteTodos(true);

        todoServiceAdapter.processDeleteTodos(true);

        verify(todoService).deleteTodos(true);
    }

    @Test
    @DisplayName("processCreateTodo should return a TodoDto")
    void processCreateTodo() {
        var TodoCreationDto = new TodoCreationDto("title");
        var todoCreation = new TodoCreation(TodoCreationDto.title());
        var todo = factory.manufacturePojo(Todo.class);
        var todoDto = factory.manufacturePojo(TodoDto.class);
        String url = "url";

        doReturn(url).when(todoServiceAdapter).formatTodoGetUrl(todo.id().toString());
        when(todoService.createTodo(todoCreation)).thenReturn(todo);
        when(todoMapper.toDto(todo, url)).thenReturn(todoDto);

        var result = todoServiceAdapter.processCreateTodo(TodoCreationDto);

        verify(todoService).createTodo(todoCreation);
        verify(todoMapper).toDto(todo, url);
        assertEquals(todoDto, result);
    }

    @Test
    @DisplayName("processUpdateTodo should return a TodoDto")
    void processUpdateTodo() {
        var todoUpdateDto = factory.manufacturePojo(TodoUpdateDto.class);
        var todoDto = factory.manufacturePojo(TodoDto.class);
        var todo = factory.manufacturePojo(Todo.class);
        var id = UUID.randomUUID();
        Todo updatedTodo = new Todo(id, todoUpdateDto.getTitle(), todoUpdateDto.getCompleted(), todoUpdateDto.getOrder());
        String url = "url";

        doReturn(url).when(todoServiceAdapter).formatTodoGetUrl(todo.id().toString());
        when(todoService.updateTodo(updatedTodo)).thenReturn(todo);
        when(todoMapper.toDto(todo, url)).thenReturn(todoDto);

        var result = todoServiceAdapter.processUpdateTodo(id, todoUpdateDto);

        verify(todoService).updateTodo(updatedTodo);
        verify(todoMapper).toDto(todo, url);
        assertEquals(todoDto, result);
    }

    @Test
    @DisplayName("processDeleteTodo should call deleteTodo")
    void processDeleteTodo() {
        var id = UUID.randomUUID();
        doNothing().when(todoService).deleteTodo(id);

        todoServiceAdapter.processDeleteTodo(id);

        verify(todoService).deleteTodo(id);
    }
}