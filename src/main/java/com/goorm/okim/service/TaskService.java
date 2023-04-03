package com.goorm.okim.service;

import com.goorm.okim.domain.Organization;
import com.goorm.okim.domain.Task;
import com.goorm.okim.exception.BusinessLogicException;
import com.goorm.okim.infra.query.DTO.GroupTaskQueryDto;
import com.goorm.okim.infra.query.DTO.UserTaskQueryDTO;
import com.goorm.okim.infra.query.TaskQuery;
import com.goorm.okim.infra.repository.OrganizationRepository;
import com.goorm.okim.infra.repository.TaskRepository;
import com.goorm.okim.presentation.item.ResponseTasksDto;
import com.goorm.okim.presentation.task.data.response.ResponseGroupTasksDto;
import com.goorm.okim.presentation.task.data.response.ResponseTaskDto;
import com.goorm.okim.presentation.task.data.response.ResponseTaskCreatedDto;
import com.goorm.okim.util.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.goorm.okim.exception.ErrorCodeMessage.GROUP_NOT_FOUND;
import static com.goorm.okim.exception.ErrorCodeMessage.TASK_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final OrganizationRepository organizationRepository;
    private final TaskQuery taskQuery;
    private final TaskMapper taskQueryDtoMapper;

    public ResponseTaskCreatedDto createTask(long userId) {
        Task task = Task.create(userId);
        Task savedTask = taskRepository.save(task);
        return ResponseTaskCreatedDto.from(savedTask);
    }

    @Transactional(readOnly = true)
    public ResponseTasksDto getUserTasks(long userId, Pageable pageable) {
        Page<UserTaskQueryDTO> userTaskQueryDtos = taskQuery.findUserTasks(userId, pageable);
        List<ResponseTaskDto> responseTaskDtos = userTaskQueryDtos.stream()
                .map(taskQueryDtoMapper::mapToTaskDto).toList();
        return ResponseTasksDto.from(responseTaskDtos, userTaskQueryDtos.isLast());
    }

    @Transactional(readOnly = true)
    public ResponseGroupTasksDto getGroupTasks(long groupId, Pageable pageable) {
        Organization organization = findOrganization(groupId);
        Page<GroupTaskQueryDto> groupTasksQueryDtos = taskQuery.findGroupTasksByGroupId(groupId, pageable);
        List<ResponseTaskDto> responseTaskDTOS = groupTasksQueryDtos.getContent().stream()
                .map(taskQueryDtoMapper::mapToTaskDtoWithUserInfo).toList();
        return ResponseGroupTasksDto.from(organization, responseTaskDTOS, groupTasksQueryDtos.isLast());
    }

    @Transactional(readOnly = true)
    public ResponseTaskDto getTaskItems(long taskId) {
        Task task = findTask(taskId);
        return ResponseTaskDto.withItems(task, task.getItems()); // 의미를 명확하게 하기 위해서 task.getItems() 도 넘기도록 한다.
    }

    private Task findTask(long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessLogicException(TASK_NOT_FOUND));
    }

    private Organization findOrganization(long groupId) {
        return organizationRepository.findById(groupId)
                .orElseThrow(() -> new BusinessLogicException(GROUP_NOT_FOUND));
    }
}
