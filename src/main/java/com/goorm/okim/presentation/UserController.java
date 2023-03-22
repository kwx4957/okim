package com.goorm.okim.presentation;

import com.goorm.okim.presentation.domain.user.SignupRequest;
import com.goorm.okim.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/user/signup")
    public ResponseEntity<?> signUp(@RequestBody SignupRequest signupRequest) {
        userService.signUp(signupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
