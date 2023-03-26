package com.goorm.okim.presentation.item;

import com.goorm.okim.presentation.task.data.dto.ResponseTaskDto;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseTasksDto {
    private List<ResponseTaskDto> tasks;
    private boolean isEnd;

    public static ResponseTasksDto from(List<ResponseTaskDto> tasks, boolean isEnd) {
        ResponseTasksDto responseTasksDTO = new ResponseTasksDto();
        responseTasksDTO.tasks = tasks;
        responseTasksDTO.isEnd = isEnd;
        return responseTasksDTO;
    }

    // isEnd: true, false
    public boolean getIsEnd() {
        return isEnd;
    }
}
