package com.goorm.okim.presentation.domain.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.goorm.okim.domain.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private long taskId;
    private int itemTotalCount;
    private int itemCompletedCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime taskUpdatedDt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime taskCreatedDt;
    private ItemDto mainItem;

    public static TaskDto from(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.taskId = task.getId();
        taskDto.itemTotalCount = task.getItems().size();
        taskDto.itemCompletedCount = task.getItems().size();
        taskDto.taskCreatedDt = task.getCreatedAt();
        taskDto.taskUpdatedDt = task.getLastModifiedAt();
        if (task.getMainItem() != null) {
            taskDto.mainItem = ItemDto.from(task.getMainItem());
        }
        return taskDto;
    }
}