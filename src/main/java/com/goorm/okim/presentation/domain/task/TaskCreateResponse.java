package com.goorm.okim.presentation.domain.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskCreateResponse {
    private long taskId;

    public static TaskCreateResponse from(long id) {
        return new TaskCreateResponse(id);
    }
}
