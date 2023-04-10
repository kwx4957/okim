package com.goorm.okim.service;

import com.goorm.okim.infra.query.DTO.GroupQueryDTO;
import com.goorm.okim.infra.query.GroupQuery;
import com.goorm.okim.infra.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final GroupQuery groupQuery;

    @Transactional(readOnly = true)
    public List<GroupQueryDTO> getAllGroups() {
        return groupQuery.getAllGroupsWithMemberSize();
    }
}
