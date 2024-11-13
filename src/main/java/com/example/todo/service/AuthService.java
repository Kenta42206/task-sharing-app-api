package com.example.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.todo.dto.LoginRequest;
import com.example.todo.dto.SignupRequest;
import com.example.todo.entity.User;
import com.example.todo.repository.UserRepository;
import com.example.todo.security.JwtTokenProvider;

@Service
public class AuthService {

    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final UserDetailsService userDetailsService;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public AuthService(AuthenticationManager authenticationManager, 
                       UserDetailsService userDetailsService, 
                       PasswordEncoder passwordEncoder, 
                       UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    /**
     * ユーザーを登録します。
     * @param signupRequest サインアップリクエスト
     * @return 登録されたユーザー
     */
    public User registerUser(SignupRequest signupRequest) {
        // パスワードをハッシュ化
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }
        String hashedPassword = passwordEncoder.encode(signupRequest.getPassword());

        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(hashedPassword);
        
        return userRepository.save(user);
    }


    public String authenticateUser(LoginRequest loginRequest) {
        // ユーザーの認証
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        // 認証の成功後、セキュリティコンテキストに設定
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // JWTトークン生成
        return jwtTokenProvider.generateToken(authentication);
    }

    /**
     * ユーザーのログインを行います。
     * @param loginRequest ログインリクエスト
     * @return ユーザー情報
     * @throws BadCredentialsException 認証情報が無効な場合
     */
    public UserDetails loginUser(LoginRequest loginRequest) {
        // UsernamePasswordAuthenticationTokenを作成
        UsernamePasswordAuthenticationToken authenticationToken = 
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        // 認証を実行
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 認証に成功したユーザー情報を取得
        return userDetailsService.loadUserByUsername(authentication.getName());
    }
}