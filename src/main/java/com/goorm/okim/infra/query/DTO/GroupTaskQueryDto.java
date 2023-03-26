package com.goorm.okim.infra.query.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupTaskQueryDto {
    private Long taskId;
    private LocalDateTime taskCreatedDt;
    private LocalDateTime taskUpdatedDt;
    private Long itemId;
    private boolean itemStatus;
    private String itemTitle;
    private LocalDateTime itemCreatedDt;
    private LocalDateTime itemUpdatedDt;
    private Long userId;
    private String profileImgUrl;
    private String nickname;
    private Long itemCompletedCount;
    private Long itemTotalCount;
}
