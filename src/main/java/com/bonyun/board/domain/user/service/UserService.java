package com.bonyun.board.domain.user.service;

import com.bonyun.board.domain.user.dto.UserDto;
import com.bonyun.board.domain.user.entity.User;
import com.bonyun.board.domain.user.repository.UserRepository;
import com.bonyun.board.global.config.JwtTokenProvider;
import com.bonyun.board.global.config.MyUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MyUserDetailsService myUserDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    public User registerUser(UserDto dto) {
        checkUser(dto.getUsername());

        User user = dto.toEntity(passwordEncoder);
        userRepository.save(user);

        return user;
    }


    public String  loginCheck(UserDto dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("아이디를 다시 확인해주세요.");
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호를 다시 확인해주세요.");
        }

        return this.authenticateUser(dto);
    }

    private void checkUser(String username) {
        if(userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 사용중인 아이디 입니다");
        }
    }

    private String authenticateUser(UserDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = myUserDetailsService.findUserIdByUsername(dto.getUsername());

        return jwtTokenProvider.generateToken(dto.getUsername(), authentication, userId);
    }
}
