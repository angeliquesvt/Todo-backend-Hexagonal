package com.angelique.todo.infrastructure.client.rest.controller;

import com.angelique.todo.domain.port.in.TodoServicePort;
import com.angelique.todo.infrastructure.client.rest.model.TodoDto;
import com.angelique.todo.infrastructure.client.rest.model.TodoUpdateDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TodoController.class)
class TodoControllerTest {

    private final static PodamFactory factory = new PodamFactoryImpl();
    @MockBean
    private TodoServicePort todoServicePort;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser
    void getTodos() throws Exception {
        var todoDto = factory.manufacturePojo(TodoDto.class);

        var todoDtos = List.of(todoDto);

        when(todoServicePort.processGetTodos()).thenReturn(todoDtos);

        mockMvc.perform(get("/todos")
                        .contentType("application/json")
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(todoDtos)));
    }

    @Test
    @WithMockUser
    void getTodo() throws Exception {
        UUID id = UUID.randomUUID();
        var todoDto = factory.manufacturePojo(TodoDto.class);

        when(todoServicePort.processGetTodo(id)).thenReturn(todoDto);

        mockMvc.perform(get("/todos/" + id)
                        .with(csrf().asHeader())
                        .contentType("application/json")
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(todoDto)));
    }

    @Test
    @WithMockUser
    void deleteTodos() throws Exception {
        doNothing().when(todoServicePort).processDeleteTodos(false);

        mockMvc.perform(delete("/todos")
                        .with(csrf().asHeader())
                        .contentType("application/json")
                        .accept("application/json"))

                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void deleteCompletedTodos() throws Exception {
        doNothing().when(todoServicePort).processDeleteTodos(true);

        mockMvc.perform(delete("/todos?=completed=true")
                        .with(csrf().asHeader())
                        .contentType("application/json")
                        .accept("application/json"))

                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void deleteTodo() throws Exception {
        var id = UUID.randomUUID();
        doNothing().when(todoServicePort).processDeleteTodo(id);

        mockMvc.perform(delete("/todos/" + id)
                        .with(csrf().asHeader())
                        .contentType("application/json")
                        .accept("application/json"))

                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void updateTodo() throws Exception {
        UUID id = UUID.randomUUID();
        var todoUpdateDto = factory.manufacturePojo(TodoUpdateDto.class);
        var todoUpdated = factory.manufacturePojo(TodoDto.class);

        when(todoServicePort.processUpdateTodo(id, todoUpdateDto)).thenReturn(todoUpdated);

        mockMvc.perform(put("/todos/" + id)
                        .with(csrf().asHeader())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(todoUpdateDto))
                        .accept("application/json"))

                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(todoUpdated)));

    }
}