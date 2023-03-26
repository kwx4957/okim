package com.goorm.okim.presentation.task.data.dto;

import com.goorm.okim.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskUserInfoDto {
    private long userId;
    private String profileImgUrl;
    private String nickname;

    public static TaskUserInfoDto from(User user) {
        TaskUserInfoDto userInfoDto = new TaskUserInfoDto();
        userInfoDto.nickname = user.getNickname();
        userInfoDto.profileImgUrl = user.getProfileImage();
        userInfoDto.userId = user.getId();
        return userInfoDto;
    }
}
