package com.goorm.okim.service;

import com.goorm.okim.domain.Task;
import com.goorm.okim.infra.repository.TaskRepository;
import com.goorm.okim.presentation.domain.task.TaskCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskCreateResponse createTask(long userId) {
        Task task = Task.create(userId);
        Task savedTask = taskRepository.save(task);
        return TaskCreateResponse.from(savedTask.getId());
    }
}
