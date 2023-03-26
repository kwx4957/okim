package com.goorm.okim.presentation.task.data.dto;

import com.goorm.okim.domain.Organization;
import lombok.Getter;

@Getter
public class GroupInfoDto {
    private String groupName;
    private long groupId;

    public static GroupInfoDto from(Organization organization) {
        GroupInfoDto groupInfoDto = new GroupInfoDto();
        groupInfoDto.groupId = organization.getId();
        groupInfoDto.groupName = organization.getOrganizationName();
        return groupInfoDto;
    }
}
