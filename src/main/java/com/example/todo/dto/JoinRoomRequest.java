package com.example.todo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRoomRequest {
    private Long userId;
    private Long roomId;
}
