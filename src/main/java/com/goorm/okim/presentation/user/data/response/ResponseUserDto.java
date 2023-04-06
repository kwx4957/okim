package com.goorm.okim.presentation.user.data.response;

import com.goorm.okim.domain.User;
import com.goorm.okim.presentation.user.OrganizationDto;
import lombok.Getter;


@Getter
public class ResponseUserDto {

    private long userId;
    private String nickname;
    private OrganizationDto organization;
    private String githubId;
    private String profileImage;
    private String selfDesc;

    public Object from(User user){
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.organization = OrganizationDto.from(user.getOrganization());
        this.githubId = user.getGithubId();
        this.profileImage = user.getProfileImage();
        this.selfDesc = user.getSelfDesc();
        return this;
    }
}
