package com.goorm.okim.presentation.task.data.response;

import com.goorm.okim.domain.Task;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseTaskCreatedDto {
    private long taskId;

    public static ResponseTaskCreatedDto from(Task task) {
        return new ResponseTaskCreatedDto(task.getId());
    }
}
