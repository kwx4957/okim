package com.goorm.okim.infra.repository;

import com.goorm.okim.domain.Task;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t " +
            "INNER JOIN t.items i " +
            "WHERE t.userId = :userId AND (i IS NOT NULL) ORDER BY t.createdAt DESC")
    Page<Task> findAllByUserId(@Param("userId") long userId, Pageable pageable);

    @Query("SELECT t FROM Task t " +
            "INNER JOIN t.items i WHERE (i IS NOT NULL)" +
            "AND t.isDeleted = false ORDER BY t.createdAt DESC")
    Page<Task> findAll(Pageable pageable);
}