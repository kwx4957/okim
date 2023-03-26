package com.goorm.okim.infra.query.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class ItemQueryDTO {
    private long itemId;
    private String itemStatus;
    private String itemTitle;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime itemCreatedDt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime itemUpdatedDt;

    public ItemQueryDTO() {
    }

    public ItemQueryDTO(long itemId, String itemStatus, String itemTitle, LocalDateTime itemCreatedDt, LocalDateTime itemUpdatedDt) {
        this.itemId = itemId;
        this.itemStatus = itemStatus;
        this.itemTitle = itemTitle;
        this.itemCreatedDt = itemCreatedDt;
        this.itemUpdatedDt = itemUpdatedDt;
    }
}
