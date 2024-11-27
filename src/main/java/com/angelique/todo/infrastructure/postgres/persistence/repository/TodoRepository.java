package com.angelique.todo.infrastructure.postgres.persistence.repository;

import com.angelique.todo.infrastructure.postgres.persistence.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface TodoRepository extends JpaRepository<TodoEntity, UUID> {
    List<TodoEntity> findAllByOrderByOrderAsc();

    @Query(value = "SELECT MAX(rank) FROM Todo", nativeQuery = true)
    Optional<Integer> getMaxOrder();

    void deleteByIsCompleted(Boolean completed);

    Optional<TodoEntity> findByTitle(String title);
}
