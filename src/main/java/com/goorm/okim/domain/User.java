package com.goorm.okim.domain;

import com.goorm.okim.presentation.domain.user.RequestUpdateUserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
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

    public void update(RequestUpdateUserDto userDto, String uploadFileUrl){
        this.nickname = userDto.getNickname();
        this.selfDesc = userDto.getSelfDesc();
        this.githubId = userDto.getGithubId();
        this.profileImage = uploadFileUrl;
    }
}
