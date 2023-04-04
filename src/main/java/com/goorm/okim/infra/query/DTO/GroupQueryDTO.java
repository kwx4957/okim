package com.goorm.okim.infra.query.DTO;

import lombok.Data;

@Data
public class GroupQueryDTO {
    private Long organizationId;
    private String organizationName;
    private Long memberCount;
}
