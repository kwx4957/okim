package com.goorm.okim.presentation;

import com.goorm.okim.presentation.domain.user.SignupRequest;
import com.goorm.okim.common.Response;
import com.goorm.okim.presentation.domain.user.RequestUpdateUserDto;
import com.goorm.okim.service.TaskService;
import com.goorm.okim.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class UserController {

    private final UserService userService;
    private final TaskService taskService;

    public UserController(UserService userService, TaskService taskService) {
        this.taskService = taskService;
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

    @PutMapping(value = "/user/{userId}", consumes = "multipart/form-data")
    public ResponseEntity<?> updateUserProfile(
            @PathVariable("userId") long userId,
            @RequestPart("file") MultipartFile file,
            RequestUpdateUserDto requestUpdateUserDto
    ){
        if(file.isEmpty()){
            return Response.failBadRequest(404,"이미지가 없습니다");
        }else{
            if(Objects.requireNonNull(file.getContentType()).contains("image/jpg")){
                return Response.failBadRequest(404,"잘못된 형식의 이미지입니다");
            }
            else if (Objects.requireNonNull(file.getContentType()).contains("image/png")){
                return Response.failBadRequest(404,"잘못된 형식의 이미지입니다");
            }
        }


        return userService.updateUserProfile(userId,requestUpdateUserDto,file);
    }

    @GetMapping("/user/{userId}/tasks")
    public ResponseEntity<?> getUserTasks(
            @PathVariable long userId,
            Pageable pageable
    ) {
        return Response.success(taskService.getAllTasks(userId, pageable));
    }
}
