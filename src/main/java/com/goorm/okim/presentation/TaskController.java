package com.goorm.okim.presentation;

import com.goorm.okim.common.Response;
import com.goorm.okim.jwt.Login;
import com.goorm.okim.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<?> createTask(@Login long userId) {
        return Response.createdWithBody(taskService.createTask(userId));
    }
}
