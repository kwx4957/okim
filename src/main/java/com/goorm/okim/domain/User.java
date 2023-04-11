package com.goorm.okim.domain;

import com.goorm.okim.presentation.user.data.request.RequestUpdateUserDto;
import com.goorm.okim.presentation.user.data.request.RequestSignUpDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
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

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    public boolean isActive() {
        return !isWithdrawl;
    }
    public static User from(RequestSignUpDto requestSignUpDTO, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.email = requestSignUpDTO.getEmail();
        user.nickname = requestSignUpDTO.getNickname();
        user.password = passwordEncoder.encode(requestSignUpDTO.getPassword());
        user.nickname = requestSignUpDTO.getNickname();
        return user;
    }

    public static User from(RequestSignUpDto requestSignUpDTO, Organization organization, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.email = requestSignUpDTO.getEmail();
        user.nickname = requestSignUpDTO.getNickname();
        user.password = passwordEncoder.encode(requestSignUpDTO.getPassword());
        user.nickname = requestSignUpDTO.getNickname();
        user.profileImage = "https://okim.s3.ap-northeast-2.amazonaws.com/profile/empty-profile-picture-png-2-2.png";
        user.organization = organization;
        return user;
    }

    public void updateWithProfileImg(RequestUpdateUserDto userDto,Organization organization, String uploadFileUrl){
        this.nickname = userDto.getNickname();
        this.selfDesc = userDto.getSelfDesc();
        this.githubId = userDto.getGithubId();
        this.organization = organization;
        this.profileImage = uploadFileUrl;
    }

    public void update(RequestUpdateUserDto userDto, Organization organization) {
        this.nickname = userDto.getNickname();
        this.selfDesc = userDto.getSelfDesc();
        this.githubId = userDto.getGithubId();
        this.organization = organization;
    }
}
