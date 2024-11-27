package com.angelique.todo.domain.model;

import java.util.UUID;


public record Todo(UUID id, String title, Boolean isCompleted, Integer order) {
}

