package com.goorm.okim.presentation.item;

import com.goorm.okim.domain.Item;
import lombok.Getter;

@Getter
public class RequestCreateItemDto {
    private String title;
    private long taskId;

    public Item toEntity(){
        return Item.builder()
                .title(title)
                .taskId(taskId)
                .build();
    }
}
