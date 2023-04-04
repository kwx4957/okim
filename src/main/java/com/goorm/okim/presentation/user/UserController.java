package com.goorm.okim.presentation.user;

import com.goorm.okim.presentation.user.data.request.RequestSignUpDto;
import com.goorm.okim.common.Response;
import com.goorm.okim.presentation.user.data.request.RequestUpdateUserDto;
import com.goorm.okim.service.RedisService;
import com.goorm.okim.service.TaskService;
import com.goorm.okim.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TaskService taskService;
    private final RedisService redisService;

    // 유저 정보조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserInfo(@PathVariable("userId") long userId){
        return userService.getUserInfo(userId);
    }

    // 이메일 중복체크
    @GetMapping("/email/{email}/check")
    public ResponseEntity<?> existEmail(@PathVariable("email") String email){
        return userService.existEmail(email);
    }

    // 닉네임 중복체크
    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<?> existsNickname(@PathVariable("nickname") String nickname){
        return userService.existNickname(nickname);
    }

    // 회원가입
    @PostMapping("/user/signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid RequestSignUpDto requestSignUpDTO) {
        userService.signUp(requestSignUpDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    // 유저 프로필 업데이트
    @PutMapping(value = "/user/{userId}", consumes = "multipart/form-data")
    public ResponseEntity<?> updateUserProfile(
            @PathVariable("userId") long userId,
            @RequestPart(value = "file", required = false) MultipartFile file,
            RequestUpdateUserDto requestUpdateUserDto
    ){
        // 파일이 있을 경우에는 형식을 체크합니다.
        if(file != null && !file.isEmpty()){
            if(Objects.requireNonNull(file.getContentType()).contains("image/jpg")){
                return Response.failBadRequest(404,"잘못된 형식의 이미지입니다");
            }
            else if (Objects.requireNonNull(file.getContentType()).contains("image/png")){
                return Response.failBadRequest(404,"잘못된 형식의 이미지입니다");
            }
        }


        return userService.updateUserProfile(userId,requestUpdateUserDto,file);
    }

    @PostMapping("/email")
    public ResponseEntity<?> sendEmailTo(@RequestBody String email) throws MessagingException {
        if (email.isBlank()) {
            return Response.failBadRequest(-1, "이메일 값이 빈 값입니다");
        }
        return userService.sendEmailTo(email);
    }

    @GetMapping("/email/validation")
    public ResponseEntity<?> getKey(@RequestBody String code) {
        if (redisService.getData(code) == null) {
            return Response.failBadRequest(-1, "유효하지 않는 인증번호");
        }
        return Response.success(redisService.getData(code));
    }

    @GetMapping("/user/{userId}/tasks")
    public ResponseEntity<?> getUserTasks(
            @PathVariable long userId,
            Pageable pageable
    ) {
        return Response.success(taskService.getUserTasks(userId, pageable));
    }

}
