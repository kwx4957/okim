package com.goorm.okim.domain;

import com.goorm.okim.presentation.domain.user.SignupRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String profileImage;
    @Column
    @NotNull
    private String email;
    @Column
    @NotNull
    private String password;
    @Column
    private String nickname;
    @Column
    private String selfDesc;
    @NotNull
    @Column
    @ColumnDefault("0")
    private boolean isWithdrawl;
    @Column
    private String githubId;

    public boolean isActive() {
        return !isWithdrawl;
    }
    public static User from(SignupRequest signupRequest, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.email = signupRequest.getEmail();
        user.nickname = signupRequest.getNickname();
        user.password = passwordEncoder.encode(signupRequest.getPassword());
        return user;
    }
}
