package com.goorm.okim.presentation.task.data;

public enum ItemStatus {
    DONE, UNDONE;

    public static ItemStatus from(boolean isDone) {
        return isDone? DONE: UNDONE;
    }
}
