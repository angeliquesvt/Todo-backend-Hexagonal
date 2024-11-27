package com.angelique.todo.infrastructure.postgres.persistence.adapter;

import com.angelique.todo.domain.model.Todo;
import com.angelique.todo.domain.model.TodoCreation;
import com.angelique.todo.infrastructure.postgres.persistence.mapper.TodoEntityMapper;
import com.angelique.todo.infrastructure.postgres.persistence.model.TodoEntity;
import com.angelique.todo.infrastructure.postgres.persistence.repository.TodoRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoRepositoryAdapterTest {


    private static final PodamFactory factory = new PodamFactoryImpl();
    @Mock
    private TodoRepository todoRepository;
    @Mock
    private TodoEntityMapper todoEntityMapper;
    @Spy
    @InjectMocks
    private TodoRepositoryAdapter todoRepositoryAdapter;

    @AfterEach
    void after() {
        verifyNoMoreInteractions(todoEntityMapper, todoRepository);
    }

    @Test
    @DisplayName("Should find all todos")
    void findTodos() {
        var todoEntity1 = factory.manufacturePojo(TodoEntity.class);
        var todoEntity2 = factory.manufacturePojo(TodoEntity.class);

        var todoEntities = List.of(todoEntity1, todoEntity2);

        var todo1 = new Todo(todoEntity1.getId(), todoEntity1.getTitle(), todoEntity1.getIsCompleted(), todoEntity1.getOrder());
        var todo2 = new Todo(todoEntity2.getId(), todoEntity2.getTitle(), todoEntity2.getIsCompleted(), todoEntity2.getOrder());

        var expectedResult = List.of(todo1, todo2);

        when(todoEntityMapper.toTodo(todoEntity1)).thenReturn(todo1);
        when(todoEntityMapper.toTodo(todoEntity2)).thenReturn(todo2);
        when(todoRepository.findAllByOrderByOrderAsc()).thenReturn(todoEntities);

        List<Todo> result = todoRepositoryAdapter.findTodos();

        verify(todoRepository).findAllByOrderByOrderAsc();
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Should find a todo by ID")
    void findTodoById() {
        var todoEntity = factory.manufacturePojo(TodoEntity.class);
        var todo = new Todo(todoEntity.getId(), todoEntity.getTitle(), todoEntity.getIsCompleted(), todoEntity.getOrder());
        var id = UUID.randomUUID();
        var expectedResult = Optional.of(todo);

        when(todoEntityMapper.toTodo(todoEntity)).thenReturn(todo);
        when(todoRepository.findById(id)).thenReturn(Optional.of(todoEntity));

        Optional<Todo> result = todoRepositoryAdapter.findTodoById(id);

        verify(todoRepository).findById(id);
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Should delete all todos")
    void deleteTodos() {
        doNothing().when(todoRepository).deleteAll();

        todoRepositoryAdapter.deleteTodos();

        verify(todoRepository).deleteAll();
    }

    @Test
    @DisplayName("Should delete todo by id")
    void deleteTodo() {
        var id = UUID.randomUUID();
        doNothing().when(todoRepository).deleteById(id);

        todoRepositoryAdapter.deleteTodo(id);

        verify(todoRepository).deleteById(id);
    }

    @Test
    @DisplayName("Should delete all completed todos")
    void deleteCompletedTodos() {
        doNothing().when(todoRepository).deleteByIsCompleted(true);

        todoRepositoryAdapter.deleteCompletedTodos();

        verify(todoRepository).deleteByIsCompleted(true);
    }

    @Test
    @DisplayName("Should save a todo")
    void save() {
        var todoCreation = factory.manufacturePojo(TodoCreation.class);
        var todo = factory.manufacturePojo(Todo.class);
        var expectedResult = Optional.of(todo);

        doReturn(2).when(todoRepositoryAdapter).generateTodoOrder();
        var todoToSave = new TodoEntity(todoCreation.title(), false, 2);
        var todoSaved = factory.manufacturePojo(TodoEntity.class);

        when(todoRepository.save(todoToSave)).thenReturn(todoSaved);
        when(todoEntityMapper.toTodo(todoSaved)).thenReturn(todo);

        var result = todoRepositoryAdapter.save(todoCreation);

        verify(todoRepository).save(todoToSave);
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Should update a todo")
    void update() {
        var todo = factory.manufacturePojo(Todo.class);
        var todoResponse = factory.manufacturePojo(Todo.class);
        var todoEntityToSave = new TodoEntity(todo.id(), todo.title(), todo.isCompleted(), todo.order());
        var todoEntitySaved = factory.manufacturePojo(TodoEntity.class);
        var expectedResult = Optional.of(todoResponse);

        when(todoRepository.save(todoEntityToSave)).thenReturn(todoEntitySaved);
        when(todoEntityMapper.toTodo(todoEntitySaved)).thenReturn(todoResponse);

        var result = todoRepositoryAdapter.update(todo);

        verify(todoRepository).save(todoEntityToSave);
        assertEquals(expectedResult, result);
    }


    @Test
    @DisplayName("Should generate a todo order")
    void generateTodoOrder_withExistingOrder() {
        when(todoRepository.getMaxOrder()).thenReturn(Optional.of(6));

        Integer result = todoRepositoryAdapter.generateTodoOrder();

        assertEquals(7, result);
    }

    @Test
    @DisplayName("Should generate a todo order if no existing order")
    void generateTodoOrder_withNoExistingOrder() {
        when(todoRepository.getMaxOrder()).thenReturn(Optional.empty());

        Integer result = todoRepositoryAdapter.generateTodoOrder();

        assertEquals(1, result);
    }
}