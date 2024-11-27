package com.angelique.todo.infrastructure.postgres.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Data
@Table(name = "Todo")
@AllArgsConstructor
@NoArgsConstructor
public class TodoEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    private String title;
    @Column(name = "isCompleted")
    private Boolean isCompleted;
    @Column(name = "rank")
    private Integer order;

    public TodoEntity(String title, Boolean isCompleted, Integer order) {
        this.title = title;
        this.isCompleted = isCompleted;
        this.order = order;
    }
}
