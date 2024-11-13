package com.example.todo.dto;

import com.example.todo.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String username;
    private User user;

    public JwtResponse(String accessToken, String username,User user) {
        this.token = accessToken;
        this.username = username;
        this.user = user;
    }
}
