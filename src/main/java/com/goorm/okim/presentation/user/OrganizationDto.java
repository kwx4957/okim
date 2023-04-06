package com.goorm.okim.presentation.user;

import com.goorm.okim.domain.Organization;
import lombok.Data;

@Data
public class OrganizationDto {
    private long organizationId;
    private String organizationName;

    public static OrganizationDto from(Organization organization) {
        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.organizationId = organization.getId();
        organizationDto.organizationName = organization.getOrganizationName();
        return organizationDto;
    }
}
