package com.goorm.okim.infra.query;


import com.goorm.okim.infra.query.DTO.GroupTaskQueryDto;
import com.goorm.okim.infra.query.DTO.UserTaskQueryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskQuery {
    Page<GroupTaskQueryDto> findGroupTasksByGroupId(long groupId, Pageable pageable);
    Page<UserTaskQueryDTO> findUserTasks(long userId, Pageable pageable);
}
