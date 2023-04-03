package com.goorm.okim.presentation.task.data.dto;

import java.util.List;

public class GroupWeeklyTaskStatusDto {
    private Long groupId;
    private String groupName;
    private List<GroupDailyTaskStatusDto> weeklyTaskStatuses;
}