package com.goorm.okim.presentation.user.data.response;

import com.goorm.okim.domain.User;
import lombok.Getter;


@Getter
public class ResponseUserDto {

    private String ninkname;
    private String Organization;
    private String githubId;
    private String profileImage;
    private String selfDesc;

    public Object from(User user){
        this.ninkname = user.getNickname();
        this.Organization = null;
        this.githubId = user.getGithubId();
        this.profileImage = user.getProfileImage();
        this.selfDesc = user.getSelfDesc();
        return this;
    }
}
