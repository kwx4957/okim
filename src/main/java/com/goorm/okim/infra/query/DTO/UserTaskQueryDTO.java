package com.goorm.okim.infra.query.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserTaskQueryDTO {
    private long taskId;
    private long itemTotalCount;
    private long itemCompletedCount;
    private LocalDateTime taskUpdatedDt;
    private LocalDateTime taskCreatedDt;
    private long itemId;
    private boolean itemIsDone;
    private String itemTitle;
    private LocalDateTime itemCreatedDt;
    private LocalDateTime itemUpdatedDt;
}
