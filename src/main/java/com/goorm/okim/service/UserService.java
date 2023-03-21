package com.goorm.okim.service;

import com.goorm.okim.common.Response;
import com.goorm.okim.domain.User;
import com.goorm.okim.infra.repository.UserRepository;
import com.goorm.okim.presentation.domain.user.ResponseUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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


}
