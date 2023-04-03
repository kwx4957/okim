package com.goorm.okim.presentation.task.data.dto;

import java.util.List;

public class UserDailyTaskStatusDto {
    private Long userId;
    private String userName;
    private List<TaskStatusDto> tasks;
    private Integer dailyCreatedTotalTasksCnt;
    private Integer dailyCompletedTasksCnt;
}