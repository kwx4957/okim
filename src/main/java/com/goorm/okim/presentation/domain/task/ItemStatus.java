package com.goorm.okim.presentation.domain.task;

public enum ItemStatus {
    DONE, UNDONE;

    public static ItemStatus from(boolean isDone) {
        return isDone? DONE: UNDONE;
    }
}
