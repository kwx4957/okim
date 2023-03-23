package com.goorm.okim.infra.repository;

import com.goorm.okim.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
