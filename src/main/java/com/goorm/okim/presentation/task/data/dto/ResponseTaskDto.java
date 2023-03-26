package com.goorm.okim.presentation.task.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.goorm.okim.domain.Item;
import com.goorm.okim.domain.Task;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTaskDto {
    private long taskId;
    private int itemTotalCount;
    private int itemCompletedCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime taskUpdatedDt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime taskCreatedDt;

    // with main item
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ItemDto mainItem;

    // with item list
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("items")
    private List<ItemDto> itemDtos;

    // with user info
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("userInfo")
    private TaskUserInfoDto userInfoDto;

    public static ResponseTaskDto withItems(Task task, List<Item> items) {
        ResponseTaskDto responseTaskDto = new ResponseTaskDto();
        responseTaskDto.taskId = task.getId();
        responseTaskDto.itemCompletedCount = task.getCompletedItemCount();
        responseTaskDto.itemTotalCount = task.getItemCount();
        responseTaskDto.itemDtos = items.stream().map(ItemDto::from).toList();
        responseTaskDto.taskCreatedDt = task.getCreatedAt();
        responseTaskDto.taskUpdatedDt = task.getLastModifiedAt();
        return responseTaskDto;
    }

    public static ResponseTaskDto withItems(Task task) {
        ResponseTaskDto responseTaskDto = new ResponseTaskDto();
        responseTaskDto.taskId = task.getId();
        responseTaskDto.itemCompletedCount = task.getCompletedItemCount();
        responseTaskDto.itemTotalCount = task.getItemCount();
        responseTaskDto.itemDtos = task.getItems().stream().map(ItemDto::from).toList();
        responseTaskDto.taskCreatedDt = task.getCreatedAt();
        responseTaskDto.taskUpdatedDt = task.getLastModifiedAt();
        return responseTaskDto;
    }
}