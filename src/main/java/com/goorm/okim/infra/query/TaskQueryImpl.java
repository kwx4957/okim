package com.goorm.okim.infra.query;


import com.goorm.okim.domain.QItem;
import com.goorm.okim.domain.QTask;
import com.goorm.okim.domain.QUser;
import com.goorm.okim.infra.query.DTO.GroupTaskQueryDto;
import com.goorm.okim.infra.query.DTO.UserTaskQueryDTO;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.goorm.okim.domain.QItem.item;
import static com.goorm.okim.domain.QTask.task;
import static com.goorm.okim.domain.QUser.user;
import static com.goorm.okim.domain.QUserOrganization.userOrganization;
import static com.querydsl.jpa.JPAExpressions.select;

@Component
@RequiredArgsConstructor
public class TaskQueryImpl implements TaskQuery{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<GroupTaskQueryDto> findGroupTasksByGroupId(long groupId, Pageable pageable) {
        QItem item2 = new QItem("qItem2");
        List<GroupTaskQueryDto> result = queryFactory
                .select(
                        Projections.bean(GroupTaskQueryDto.class,
                                task.id.as("taskId"),
                                task.createdAt.as("taskCreatedDt"),
                                task.lastModifiedAt.as("taskUpdatedDt"),
                                item.id.as("itemId"),
                                item.isDone.as("itemStatus"),
                                item.title.as("itemTitle"),
                                item.createdAt.as("itemCreatedDt"),
                                item.lastModifiedAt.as("itemUpdatedDt"),
                                user.id.as("userId"),
                                user.profileImage.as("profileImgUrl"),
                                user.nickname.as("nickname"),
                                ExpressionUtils.as(select(item2.id.count())
                                        .from(item2)
                                        .where(item2.taskId.eq(task.id), item2.isDone.isTrue()), "itemCompletedCount"),
                                ExpressionUtils.as(select(item2.id.count())
                                        .from(item2)
                                        .where(item2.taskId.eq(task.id)), "itemTotalCount")
                        )
                )
                .from(task)
                .innerJoin(item).on(item.taskId.eq(task.id))
                .innerJoin(user).on(user.id.eq(task.userId))
                .innerJoin(userOrganization).on(userOrganization.user.eq(user))
                .where(
                        organizationIdEq(groupId),
                        onlyMainItem(item, item2, task),
                        userNotDeleted(user)
                )
                .groupBy(task.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return new PageImpl<>(result);
    }

    @Override
    public Page<UserTaskQueryDTO> findUserTasks(long userId, Pageable pageable) {
        QItem item2 = new QItem("qItem2");
        List<UserTaskQueryDTO> result = queryFactory
                .select(
                        Projections.bean(UserTaskQueryDTO.class,
                                task.id.as("taskId"),
                                task.createdAt.as("taskCreatedDt"),
                                task.lastModifiedAt.as("taskUpdatedDt"),
                                item.id.as("itemId"),
                                item.title.as("itemTitle"),
                                item.isDone.as("itemIsDone"),
                                item.createdAt.as("itemCreatedDt"),
                                item.lastModifiedAt.as("itemUpdatedDt"),
                                ExpressionUtils.as(select(item2.id.count())
                                        .from(item2)
                                        .where(item2.taskId.eq(task.id), item2.isDone.isTrue()), "itemCompletedCount"),
                                ExpressionUtils.as(select(item2.id.count())
                                        .from(item2)
                                        .where(item2.taskId.eq(task.id)), "itemTotalCount")
                        )
                )
                .from(task)
                .innerJoin(item).on(item.taskId.eq(task.id))
                .innerJoin(user).on(user.id.eq(task.userId))
                .where(
                        onlyMainItem(item, item2, task),
                        userNotDeleted(user),
                        userEq(userId)
                )
                .groupBy(task.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return new PageImpl<>(result);
    }

    private BooleanExpression userEq(long userId) {
        return user.id.eq(userId);
    }

    private BooleanExpression organizationIdEq(Long groupId) {
        return userOrganization.organization.id.eq(groupId);
    }

    // select main item
    private BooleanExpression onlyMainItem(QItem item, QItem item2, QTask task) {
        return item.id.eq(select(item.id.min())
                .from(item2)
                .where(item2.taskId.eq(task.id)));
    }

    // only active user
    private BooleanExpression userNotDeleted(QUser user) {
        return user.isWithdrawl.isFalse();
    }
}
