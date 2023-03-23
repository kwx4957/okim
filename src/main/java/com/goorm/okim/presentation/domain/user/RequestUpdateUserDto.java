package com.goorm.okim.presentation.domain.user;

import lombok.Data;

@Data
public class RequestUpdateUserDto {
    public String nickname;
    public String selfDesc;
    public String githubId;
    public String profileImage;
}
