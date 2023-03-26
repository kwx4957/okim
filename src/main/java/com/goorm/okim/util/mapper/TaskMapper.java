package com.goorm.okim.util.mapper;

import com.goorm.okim.infra.query.DTO.GroupTaskQueryDto;
import com.goorm.okim.infra.query.DTO.UserTaskQueryDTO;
import com.goorm.okim.presentation.task.data.dto.ItemDto;
import com.goorm.okim.presentation.task.data.response.ResponseTaskDto;
import com.goorm.okim.presentation.task.data.dto.TaskUserInfoDto;
import com.goorm.okim.presentation.task.data.ItemStatus;
import org.springframework.stereotype.Component;

/**
 *
 * TaskMapper
 *
 * task query 클래스를 활용하여 가져온 queryDto 를 dto 로 변환할 수 있도록 도와주는 TaskMapper
 *
 * @author minshik
 * 작성일 2023/03/26
 **/
@Component
public class TaskMapper {

    public ResponseTaskDto mapToTaskDto(UserTaskQueryDTO userTaskQueryDTO) {
        return ResponseTaskDto.builder()
                .taskId(userTaskQueryDTO.getTaskId())
                .taskCreatedDt(userTaskQueryDTO.getTaskCreatedDt())
                .taskUpdatedDt(userTaskQueryDTO.getTaskUpdatedDt())
                .itemTotalCount((int) userTaskQueryDTO.getItemTotalCount())
                .itemCompletedCount((int) userTaskQueryDTO.getItemCompletedCount())
                .mainItem(mapToMainItemDtoFromUserTaskQueryDTO(userTaskQueryDTO))
                .build();
    }

    public ResponseTaskDto mapToTaskDtoWithUserInfo(GroupTaskQueryDto groupTaskQueryDTO) {
        return ResponseTaskDto.builder()
                .taskId(groupTaskQueryDTO.getTaskId())
                .taskCreatedDt(groupTaskQueryDTO.getTaskCreatedDt())
                .taskUpdatedDt(groupTaskQueryDTO.getTaskUpdatedDt())
                .itemTotalCount(groupTaskQueryDTO.getItemTotalCount().intValue())
                .itemCompletedCount(groupTaskQueryDTO.getItemCompletedCount().intValue())
                .mainItem(mapToMainItemDto(groupTaskQueryDTO))
                .userInfoDto(mapToUserInfoDto(groupTaskQueryDTO))
                .build();
    }

    private ItemDto mapToMainItemDtoFromUserTaskQueryDTO(UserTaskQueryDTO userTaskQueryDTO) {
        return ItemDto.builder()
                .itemId(userTaskQueryDTO.getItemId())
                .itemTitle(userTaskQueryDTO.getItemTitle())
                .itemCreatedDt(userTaskQueryDTO.getItemCreatedDt())
                .itemUpdatedDt(userTaskQueryDTO.getItemUpdatedDt())
                .itemStatus(ItemStatus.from(userTaskQueryDTO.isItemIsDone()).name())
                .build();
    }

    private TaskUserInfoDto mapToUserInfoDto(GroupTaskQueryDto groupTaskQueryDTO) {
        return TaskUserInfoDto.builder()
                .userId(groupTaskQueryDTO.getUserId())
                .nickname(groupTaskQueryDTO.getNickname())
                .profileImgUrl(groupTaskQueryDTO.getProfileImgUrl()).build();
    }

    private ItemDto mapToMainItemDto(GroupTaskQueryDto groupTaskQueryDTO) {
        return ItemDto.builder()
                .itemId(groupTaskQueryDTO.getItemId())
                .itemId(groupTaskQueryDTO.getItemId())
                .itemTitle(groupTaskQueryDTO.getItemTitle())
                .itemStatus(ItemStatus.from(groupTaskQueryDTO.isItemStatus()).name())
                .itemCreatedDt(groupTaskQueryDTO.getItemCreatedDt())
                .itemUpdatedDt(groupTaskQueryDTO.getItemUpdatedDt())
                .build();
    }
}
