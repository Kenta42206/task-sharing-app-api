package com.example.todo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    @NotBlank(message = "名前は必須です")
    private String username;

    @Email(message = "無効なメールアドレスです")
    @NotBlank(message = "メールアドレスは必須です")
    private String email;

    @NotBlank(message = "パスワードは必須です")
    @Size(min = 6, message = "パスワードは6文字以上でなければなりません")
    private String password;
}
