package com.goorm.okim.presentation;

import com.goorm.okim.common.Response;
import com.goorm.okim.infra.repository.UserRepository;
import com.goorm.okim.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private UserService userService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserTask(@PathVariable("userId") long userId){
        return userService.getUserTask(userId);
    }
}
