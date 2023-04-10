package com.goorm.okim.presentation.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goorm.okim.config.RedisConfig;
import com.goorm.okim.infra.repository.UserRepository;
import com.goorm.okim.presentation.user.data.request.RequestSignUpDto;
import com.goorm.okim.service.UserService;
import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("유저 테스트")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RedisConfig redisConfig;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository repository;

    // getUserInfo 메소드 테스트
    @Test
    @DisplayName("[유저 정보 조회] 탈퇴하지 않는 정상적인 유저를 조회한다.")
    public void getUserInfoTest() throws Exception {
        long userId = 1L;
        mockMvc.perform(get("/api/v1/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId", is(1)));
    }

    @Test
    @DisplayName("[이메일 중복체크] 중복되지 않는 이메일을 요청했을 때 응답 상태 200 과 false 을 반환한다.")
    void shouldReturnStatusOkWhenSubmitNotDuplicateEmailCheck() throws Exception {
        String email = "thisisnowfound@naver.com";
        mockMvc.perform(get("/api/v1/email/{email}/check", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(false));
    }

    @Test
    @DisplayName("[이메일 중복체크] 중복되는 이메일을 요청했을 때 응답상태 200 과 true 를 반환한다. - 예외")
    void shouldReturnBadRequestWhenSubmitDuplicateEmail() throws Exception {
        String email = "kmss69052@naver.com";
        mockMvc.perform(get("/api/v1/email/{email}/check", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(true));
    }


    @Test
    @DisplayName("[닉네임 중복체크] 중복되지 않는 닉네임일 경우 닉네임 중복체크 시 false 를 반환한다.")
    void shouldReturnFalseWhenSubmitNotDuplicateNickname() throws Exception {
        String nickname = UUID.randomUUID().toString();
        mockMvc.perform(get("/api/v1/nickname/{nickname}", nickname))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(false));
    }

    @Test
    @DisplayName("[닉네임 중복체크] 중복되는 닉네임일 경우 닉네임 중복체크 시 false 를 반환한다.")
    void shouldReturnTrueWhenSubmitDuplicateNickname() throws Exception {
        String nickname = "뭉게구름안녕";
        mockMvc.perform(get("/api/v1/nickname/{nickname}", nickname))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(true));
    }

    @Test
    @DisplayName("[회원가입] - 잘못된 인증 코드 오류, 400 에러")
    @Disabled
    public void signUpTestFailNotAllowValidationCode() throws Exception {
        // given
        RequestSignUpDto requestSignUpDto = new RequestSignUpDto();
        requestSignUpDto.setEmail("test@example.com");
        requestSignUpDto.setPassword("123456");
        requestSignUpDto.setNickname("testUser");
        requestSignUpDto.setVerificationCode("testCode");
        requestSignUpDto.setGroupId(1L);

        // when, then
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/user/signup")
                        .content(objectMapper.writeValueAsBytes(requestSignUpDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("[회원가입] - 이메일 형식 오류, 405 에러")
    void signUpTestFailEmailFormat() throws Exception {
        // given
        RequestSignUpDto requestSignUpDto = new RequestSignUpDto();
        requestSignUpDto.setEmail("testexample.com");
        requestSignUpDto.setPassword("123456");
        requestSignUpDto.setNickname("testUser");
        requestSignUpDto.setVerificationCode("testCode");
        requestSignUpDto.setGroupId(1L);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestSignUpDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.code").value(-1))
                .andExpect(jsonPath("$.message").value("이메일 형식이 맞지 않습니다."))
                .andDo(print())
                .andReturn();
    }
}