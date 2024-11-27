package com.angelique.todo.infrastructure.client.rest.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TodoUpdateDto {

    @NotNull
    private String title;
    @NotNull
    private Boolean completed;
    @Min(0)
    @NotNull
    private Integer order;
}