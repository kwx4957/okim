package com.goorm.okim.presentation.user.data.request;

import lombok.Data;

@Data
public class RequestUpdateUserDto {
    public String nickname;
    public String selfDesc;
    public String githubId;
    public String profileImage;
    public long groupId;
}