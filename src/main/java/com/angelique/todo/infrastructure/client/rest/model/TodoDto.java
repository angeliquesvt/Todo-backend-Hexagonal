package com.angelique.todo.infrastructure.client.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {
    private UUID id;
    private String title;
    private Boolean isCompleted;
    private Integer order;
    private String url;
}
