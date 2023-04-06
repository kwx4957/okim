package com.goorm.okim.presentation.task.data.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goorm.okim.domain.Organization;
import com.goorm.okim.presentation.task.data.dto.GroupInfoDto;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseGroupTasksDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private GroupInfoDto groupInfo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ResponseTaskDto> tasks;

    private Boolean isEnd;

    public static ResponseGroupTasksDto from(Organization organization, List<ResponseTaskDto> responseTaskDtos, boolean isEnd) {
        ResponseGroupTasksDto responseGroupTasksDto = new ResponseGroupTasksDto();
        responseGroupTasksDto.groupInfo = GroupInfoDto.from(organization);
        responseGroupTasksDto.tasks = responseTaskDtos;
        responseGroupTasksDto.isEnd = isEnd;
        return responseGroupTasksDto;
    }

    public static ResponseGroupTasksDto from(List<ResponseTaskDto> responseTaskDtos, boolean isEnd) {
        ResponseGroupTasksDto responseGroupTasksDto = new ResponseGroupTasksDto();
        responseGroupTasksDto.tasks = responseTaskDtos;
        responseGroupTasksDto.isEnd = isEnd;
        return responseGroupTasksDto;
    }
}
