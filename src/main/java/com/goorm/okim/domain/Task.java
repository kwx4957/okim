package com.goorm.okim.domain;

import com.goorm.okim.common.BaseEntity;
import com.goorm.okim.exception.BusinessLogicException;
import com.goorm.okim.exception.ErrorCodeMessage;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private long userId;

    @Column
    private boolean isDeleted;

    @JoinColumn(name = "taskId")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("id asc")
    private List<Item> items = new ArrayList<>();

    public void validate() {
        checkNotDeleted();
        checkStartedItemInserted();
    }

    public static Task create(long userId) {
        Task task = new Task();
        task.userId = userId;
        return task;
    }

    private void checkStartedItemInserted() {
        if (this.items == null || items.isEmpty()) {
            throw new BusinessLogicException(ErrorCodeMessage.TASK_NOT_FOUND);
        }
    }

    private void checkNotDeleted() {
        if (this.isDeleted) {
            throw new BusinessLogicException(ErrorCodeMessage.TASK_NOT_FOUND);
        }
    }

    public int getCompletedItemCount(){
        return (int) items.stream()
                .filter(Item::isDone)
                .count();
    }

    public int getItemCount() {
        return items.size();
    }
}
