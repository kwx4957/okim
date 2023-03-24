package com.goorm.okim.service;

import com.goorm.okim.common.Response;
import com.goorm.okim.domain.User;
import com.goorm.okim.exception.BusinessLogicException;
import com.goorm.okim.exception.ErrorCodeMessage;
import com.goorm.okim.infra.repository.UserRepository;
import com.goorm.okim.presentation.domain.S3FileDto;
import com.goorm.okim.presentation.domain.user.RequestUpdateUserDto;
import com.goorm.okim.presentation.domain.user.SignupRequest;
import com.goorm.okim.presentation.domain.user.ResponseUserDto;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AWSService awsService;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

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


    public void signUp(SignupRequest signupRequest) {
        checkEmailUnique(signupRequest.getEmail());
        User user = User.from(signupRequest, passwordEncoder);
        userRepository.save(user);
    }

    private void checkEmailUnique(String email) {
        Boolean exists = userRepository.existsByEmail(email);
        if (Boolean.TRUE.equals(exists)) {
            throw new BusinessLogicException(ErrorCodeMessage.USER_DUPLICATE_EMAIL);
        }
    }

    public ResponseEntity<?> sendEmailTo(String email) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setSubject("OKim에서 보내는 인증번호입니다");
        message.setText(createEmailText(),"UTF-8","html");
        message.addRecipients(Message.RecipientType.TO,email);
        javaMailSender.send(message);
        return Response.success("send email success");
    }

    private String createEmailText(){
        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<h1> 안녕하세요</h1>";
        msgg += "<h1> Goorm TODO Check List 입니다</h1>";
        msgg += "<br>";
        msgg += "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += createKey() + "</strong><div><br/> ";
        msgg += "</div>";
        return msgg;
    }

    private String createKey(){
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();
        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤, rnd 값에 따라서 아래 switch 문이 실행됨

            switch (index) {
                case 0 -> key.append((char) ((int) (rnd.nextInt(26)) + 97));

                // a~z (ex. 1+97=98 => (char)98 = 'b')
                case 1 -> key.append((char) ((int) (rnd.nextInt(26)) + 65));

                // A~Z
                case 2 -> key.append((rnd.nextInt(10)));

                // 0~9
            }
        }
        return key.toString();
    }
}
