package com.example.todo.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LockId implements Serializable {
    private Long taskId;
    private Long userId;

    public LockId() {}

    public LockId(Long taskId, Long userId) {
        this.taskId = taskId;
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        return taskId.hashCode() + userId.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LockId)) return false;
        LockId that = (LockId) o;
        return taskId.equals(that.taskId) && userId.equals(that.userId);
    }
}
