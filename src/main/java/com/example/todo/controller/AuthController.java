package com.example.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.dto.JwtResponse;
import com.example.todo.dto.LoginRequest;
import com.example.todo.dto.SignupRequest;
import com.example.todo.entity.User;
import com.example.todo.exception.ResourceNotFoundException;
import com.example.todo.service.AuthService;
import com.example.todo.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;


    /**
     * サインアップ処理
     * @param signupRequest 
     * @return ResponseEntity<User>
     */
    @PostMapping("/signup")
    public ResponseEntity<User> signupUser(@Valid @RequestBody SignupRequest signupRequest) {
        User newUser = authService.registerUser(signupRequest);
        return ResponseEntity.ok(newUser);
    }

    /**
     * ログイン処理
     * @param loginRequest 
     * @return ResponseEntity<JwtResponse>
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        String token = authService.authenticateUser(loginRequest);

        // ログイン中のユーザをクライアント側にもたせる
        User user = userService.getUserByUsername(loginRequest.getUsername())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ResponseEntity.ok(new JwtResponse(token, loginRequest.getUsername(), user));
    }

    @PostMapping("/logout")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "{\"message\": \"Logout successful\"}";
    }


}
