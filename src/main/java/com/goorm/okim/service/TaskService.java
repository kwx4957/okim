package com.goorm.okim.service;

import com.goorm.okim.domain.Task;
import com.goorm.okim.infra.repository.TaskRepository;
import com.goorm.okim.presentation.domain.task.TaskCreateResponse;
import com.goorm.okim.presentation.domain.task.TaskDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskCreateResponse createTask(long userId) {
        Task task = Task.create(userId);
        Task savedTask = taskRepository.save(task);
        return TaskCreateResponse.from(savedTask.getId());
    }

    public List<TaskDto> getAllTasks(long userId, Pageable pageable) {
        Page<Task> tasks = taskRepository.findAllByUserId(userId, pageable);
        return tasks.getContent()
                .stream().map(TaskDto::from)
                .collect(Collectors.toList());
    }
}
