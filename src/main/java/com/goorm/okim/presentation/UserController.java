package com.goorm.okim.presentation;

import com.goorm.okim.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserTask(@PathVariable("userId") long userId){
        return userService.getUserTask(userId);
    }

    @GetMapping("/email/{email}/check")
    public ResponseEntity<?> existEmail(@PathVariable("email") String email){
        return userService.existEmail(email);
    }

    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<?> existsNickname(@PathVariable("nickname") String nickname){
        return userService.existNickname(nickname);
    }

}
