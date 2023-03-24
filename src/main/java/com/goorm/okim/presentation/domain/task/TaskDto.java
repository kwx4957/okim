package com.goorm.okim.presentation.domain.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.goorm.okim.domain.Item;
import com.goorm.okim.domain.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto {
    private long taskId;
    private int itemTotalCount;
    private int itemCompletedCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime taskUpdatedDt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime taskCreatedDt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ItemDto mainItem;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ItemDto> items;

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

    public static TaskDto createInfo(Task task, List<ItemDto> itemDtos) {
        TaskDto taskDto = new TaskDto();
        taskDto.taskId = task.getId();
        taskDto.itemTotalCount = task.getItems().size();
        taskDto.itemCompletedCount = task.countCompletedItems();
        taskDto.taskCreatedDt = task.getCreatedAt();
        taskDto.taskUpdatedDt = task.getLastModifiedAt();
        taskDto.items = itemDtos;
        return taskDto;
    }
}