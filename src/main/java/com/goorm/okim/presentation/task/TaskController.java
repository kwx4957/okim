package com.goorm.okim.presentation.task;

import com.goorm.okim.common.Response;
import com.goorm.okim.jwt.Login;
import com.goorm.okim.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<?> getRecentTasks() {
        // TODO 현재는 메인페이지에서는 최근 생성된 할일만 보여줍니다. (10개). 페이지와 사이즈를 받을 지는 후에 결정
        Pageable pageable = PageRequest.of(0, 10);
        return Response.success(taskService.getRecentTasks(pageable));
    }

    @PostMapping
    public ResponseEntity<?> createTask(@Login long userId) {
        return Response.createdWithBody(taskService.createTask(userId));
    }

    @GetMapping("/{taskId}/items")
    public ResponseEntity<?> getTaskItems(@PathVariable long taskId) {
        return Response.success(taskService.getTaskItems(taskId));
    }
}
