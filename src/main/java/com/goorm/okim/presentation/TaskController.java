package com.goorm.okim.presentation;

import com.goorm.okim.common.Response;
import com.goorm.okim.jwt.Login;
import com.goorm.okim.presentation.domain.task.TaskDto;
import com.goorm.okim.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<?> createTask(@Login long userId) {
        return Response.createdWithBody(taskService.createTask(userId));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTaskDetail(@PathVariable long taskId) {
        TaskDto taskDto = taskService.getTask(taskId);
        return Response.success(taskDto);
    }
}
