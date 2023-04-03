package com.goorm.okim.presentation.task.data.dto;

import java.time.LocalDate;
import java.util.List;

public class GroupDailyTaskStatusDto {
    private LocalDate date;
    private List<UserDailyTaskStatusDto> usersDailyTaskStatus;
}