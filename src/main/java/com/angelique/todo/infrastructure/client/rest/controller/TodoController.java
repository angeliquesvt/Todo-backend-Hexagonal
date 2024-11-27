package com.angelique.todo.infrastructure.client.rest.controller;

import com.angelique.todo.domain.port.in.TodoServicePort;
import com.angelique.todo.infrastructure.client.rest.model.TodoCreationDto;
import com.angelique.todo.infrastructure.client.rest.model.TodoDto;
import com.angelique.todo.infrastructure.client.rest.model.TodoUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.angelique.todo.infrastructure.client.rest.contants.Constant.TODO_BASE_URL;

@RestController
@RequestMapping(TODO_BASE_URL)
@RequiredArgsConstructor
public class TodoController {

    private final TodoServicePort todoServicePort;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTodo(@RequestBody @Valid TodoCreationDto todoCreationDto) {
        todoServicePort.processCreateTodo(todoCreationDto);
    }

    @GetMapping
    public List<TodoDto> getTodos() {
        return todoServicePort.processGetTodos();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TodoDto getTodo(@PathVariable String id) {
        return todoServicePort.processGetTodo(UUID.fromString(id));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodos(@RequestParam(required = false) Boolean completed) {
        todoServicePort.processDeleteTodos(completed);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodo(@PathVariable String id) {
        todoServicePort.processDeleteTodo(UUID.fromString(id));
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TodoDto updateTodo(@PathVariable String id, @RequestBody @Valid TodoUpdateDto todoUpdateBody) {
        return todoServicePort.processUpdateTodo(UUID.fromString(id), todoUpdateBody);
    }
}
