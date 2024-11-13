package com.example.todo.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoomId implements Serializable {
    private Long userId;
    private Long roomId;

    @Override
    public int hashCode() {
        return userId.hashCode() + roomId.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRoomId)) return false;
        UserRoomId that = (UserRoomId) o;
        return userId.equals(that.userId) && roomId.equals(that.roomId);
    }
}
