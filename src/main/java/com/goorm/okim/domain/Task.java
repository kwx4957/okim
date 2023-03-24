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
    @Column
    private int progress;

    @JoinColumn(name = "taskId")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("id asc")
    private List<Item> items = new ArrayList<>();

    public void checkValidTask() {
        checkNotDeleted();
        checkStartedItemInserted();
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

    public static Task create(long userId) {
        Task task = new Task();
        task.userId = userId;
        return task;
    }

    public long getId() {
        return id;
    }

    public Item getMainItem() {
        // todo 어떤 item 을 가져올 지 정해야 함
        if (!items.isEmpty()) {
            return items.get(0);
        }
        return null;
    }

    public int countCompletedItems() {
        return (int) items.stream()
                .filter(Item::isDone)
                .count();
    }
}
