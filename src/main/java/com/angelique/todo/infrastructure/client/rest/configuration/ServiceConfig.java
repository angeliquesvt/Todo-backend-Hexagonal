package com.angelique.todo.infrastructure.client.rest.configuration;

import com.angelique.todo.domain.port.out.TodoRepositoryPort;
import com.angelique.todo.domain.service.TodoService;
import com.angelique.todo.domain.service.impl.TodoServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public TodoService todoService(TodoRepositoryPort todoRepositoryPort) {
        return new TodoServiceImpl(todoRepositoryPort);
    }
}
