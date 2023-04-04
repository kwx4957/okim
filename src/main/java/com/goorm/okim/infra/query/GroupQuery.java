package com.goorm.okim.infra.query;

import com.goorm.okim.domain.Organization;
import com.goorm.okim.domain.QOrganization;
import com.goorm.okim.domain.QUser;
import com.goorm.okim.infra.query.DTO.GroupQueryDTO;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.goorm.okim.domain.QOrganization.organization;
import static com.goorm.okim.domain.QUser.user;

@Component
@RequiredArgsConstructor
public class GroupQuery {

    private final JPQLQueryFactory queryFactory;

    @Transactional(readOnly = true)
    public List<GroupQueryDTO> getAllGroupsWithMemberSize() {
        return queryFactory.select(
                Projections.bean(GroupQueryDTO.class,
                        organization.id.as("organizationId"),
                        organization.OrganizationName.as("organizationName"),
                        user.count().as("memberCount")
                    )
                )
                .from(organization)
                .join(user).on(user.organization.eq(organization)).fetchJoin()
                .groupBy(organization)
                .fetch();
    }
}
