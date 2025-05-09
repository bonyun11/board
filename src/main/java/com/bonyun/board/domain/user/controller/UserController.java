package com.bonyun.board.domain.user.controller;

import java.util.Map;

import com.bonyun.board.domain.role.Role;
import com.bonyun.board.domain.user.dto.UserDto;
import com.bonyun.board.domain.user.entity.User;
import com.bonyun.board.domain.user.repository.UserRepository;
import com.bonyun.board.domain.user.service.UserService;
import com.bonyun.board.global.config.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider; // JWT 생성 및 검증을 담당하는 유틸 클래스
    private final UserRepository userRepository;

    private final UserService userService; // 사용자 관련 로직을 처리하는 서비스

    // 회원가입 API
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserDto dto) {
        User newUser = userService.registerUser(dto); // 회원가입 처리

        return ResponseEntity.ok(newUser); // 등록된 사용자 정보 반환 (200 OK)
    }

    // 로그인 API (JWT 토큰 발급)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid UserDto dto) {
        String token = userService.loginCheck(dto); // 사용자 인증 수행

        return ResponseEntity.ok(token);  // JWT 반환
    }
}
