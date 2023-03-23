package com.goorm.okim.service;

import com.goorm.okim.common.Response;
import com.goorm.okim.domain.User;
import com.goorm.okim.infra.repository.UserRepository;
import com.goorm.okim.presentation.domain.S3FileDto;
import com.goorm.okim.presentation.domain.user.RequestUpdateUserDto;
import com.goorm.okim.presentation.domain.user.ResponseUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final AWSService awsService;

    public UserService(UserRepository userRepository, AWSService awsService) {
        this.userRepository = userRepository;
        this.awsService = awsService;
    }

    public ResponseEntity<?> getUserTask(long userId) {
       Optional<User> user = userRepository.findById(userId);

       if(user.isEmpty()){
           return Response.failNotFound(404,"해당 유저는 없는 유저입니다");
       }

       if(user.get().isWithdrawl()){
           return Response.failBadRequest(404,"해당 유저는 탈퇴한 유저입니다");
       }

       return Response.success(new ResponseUserDto().from(user.get()));
    }

    public ResponseEntity<?> existNickname(String nickname){
        Boolean existNickname = userRepository.existsByNickname(nickname);
        return Response.success(existNickname);
    }

    public ResponseEntity<?> existEmail(String email){
        //TODO 유효성 검사 작동하지 않음
        if(!validateEmail(email)){
            Response.failBadRequest(406,"이메일 형식이 맞지 않습니다");
        }

        Boolean existEmail = userRepository.existsByEmail(email);
        return Response.success(existEmail);
    }

    private boolean validateEmail(String email){
        return Pattern.compile("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}").matcher(email).matches();
    }

    public ResponseEntity<?> updateUserProfile(long userId, RequestUpdateUserDto userDto, MultipartFile file){
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()){
            return Response.failNotFound(404,"해당 유저는 없는 유저입니다");
        }

        S3FileDto s3FileDto = awsService.uploadFiles(file);

        user.get().update(userDto,s3FileDto.getUploadFileUrl());

        userRepository.save(user.get());
        return Response.success("Update Success");
    }


}
