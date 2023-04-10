package com.goorm.okim.service;

import com.goorm.okim.common.Response;
import com.goorm.okim.domain.Organization;
import com.goorm.okim.domain.User;
import com.goorm.okim.exception.BusinessLogicException;
import com.goorm.okim.infra.repository.OrganizationRepository;
import com.goorm.okim.infra.repository.UserRepository;
import com.goorm.okim.presentation.S3FileDto;
import com.goorm.okim.presentation.user.data.request.RequestUpdateUserDto;
import com.goorm.okim.presentation.user.data.request.RequestSignUpDto;
import com.goorm.okim.presentation.user.data.response.ResponseUserDto;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

import static com.goorm.okim.exception.ErrorCodeMessage.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final RedisService redisService;
    private final UserRepository userRepository;
    private final AWSService awsService;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final OrganizationRepository organizationRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<?> getUserInfo(long userId) {
       Optional<User> user = userRepository.findById(userId);

       if(user.isEmpty()){
           return Response.failNotFound(404,"해당 유저는 없는 유저입니다");
       }

       if(user.get().isWithdrawl()){
           return Response.failBadRequest(404,"해당 유저는 탈퇴한 유저입니다");
       }
       return Response.success(new ResponseUserDto().from(user.get()));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> existNickname(String nickname){
        Boolean existNickname = userRepository.existsByNickname(nickname);
        return Response.success(existNickname);
    }

    @Transactional(readOnly = true)
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

    @Transactional
    public ResponseEntity<?> updateUserProfile(long userId, RequestUpdateUserDto userDto, MultipartFile file){
        // 유저 업데이트
        User user = findUser(userId);
        Organization organization = findGroup(userDto.groupId);

        // 프로필 이미지 저장
        if (file != null) {
            S3FileDto s3FileDto = awsService.uploadFiles(file);
            String profileUrl = s3FileDto.getUploadFileUrl();
            user.updateWithProfileImg(userDto, organization, profileUrl);
        }else{
            user.update(userDto, organization);
        }
        return Response.success("Update Success");
    }

    @Transactional
    public void signUp(RequestSignUpDto requestSignUpDto) {
        // 1. 이메일 중복여부 체크
        validateUniqueEmail(requestSignUpDto.getEmail());

        // 2. 인증코드 유효성 체크
        String email = redisService.getData(requestSignUpDto.getVerificationCode());
        verifyAuthCode(requestSignUpDto, email);

        // 3. 유저 저장(회원가입)
        Organization organization = findGroup(requestSignUpDto.getGroupId());
        User user = User.from(requestSignUpDto, organization, passwordEncoder);
        userRepository.save(user);
    }

    private void verifyAuthCode(RequestSignUpDto requestSignUpDto, String email) {
        if(email == null || email == requestSignUpDto.getEmail()){
            throw new BusinessLogicException(AUTH_REQUIRED_VERIFICATION_CODE);
        }
    }

    private Organization findGroup(long groupId) {
        return organizationRepository.findById(groupId)
                .orElseThrow(() -> new BusinessLogicException(GROUP_NOT_FOUND));
    }

    private void validateUniqueEmail(String email) {
        Boolean exists = userRepository.existsByEmail(email);
        if (Boolean.TRUE.equals(exists)) {
            throw new BusinessLogicException(USER_DUPLICATE_EMAIL);
        }
    }

    public ResponseEntity<?> sendEmailTo(String email) throws MessagingException {
        String redisKey = createKey();
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setSubject("OKim에서 보내는 인증번호입니다");
        message.setText(createEmailText(redisKey),"UTF-8","html");
        message.addRecipients(Message.RecipientType.TO,email);
        javaMailSender.send(message);
        redisService.setDataExpire(redisKey,email,60*5L);
        return Response.success("send email success");
    }

    private String createEmailText(String redisKey){
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
        msgg += redisKey + "</strong><div><br/> ";
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

    private User findUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessLogicException(NOT_FOUND_USER));
    }

}
