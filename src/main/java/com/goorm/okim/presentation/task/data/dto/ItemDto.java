package com.goorm.okim.presentation.task.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.goorm.okim.domain.Item;
import com.goorm.okim.presentation.task.data.ItemStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ItemDto {
    private Long itemId;
    private String itemStatus;
    private String itemTitle;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime itemCreatedDt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime itemUpdatedDt;

    public static ItemDto from(Item item) {
        return ItemDto.builder()
                .itemId(item.getId())
                .itemTitle(item.getTitle())
                .itemStatus(ItemStatus.from(item.isDone()).name())
                .itemCreatedDt(item.getCreatedAt())
                .itemUpdatedDt(item.getLastModifiedAt())
                .build();
    }
}
