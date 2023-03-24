package com.goorm.okim.presentation.domain.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.goorm.okim.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private Long itemId;
    private String itemStatus;
    private String itemTitle;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime itemCreatedDt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime itemUpdatedDt;

    public static ItemDto from(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.itemId = item.getId();
        itemDto.itemTitle = item.getTitle();
        itemDto.itemStatus = ItemStatus.from(item.isDone()).name();
        itemDto.itemCreatedDt = item.getCreatedAt();
        itemDto.itemUpdatedDt = item.getLastModifiedAt();
        return itemDto;
    }

}
