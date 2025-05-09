package com.bonyun.board.domain.user.dto;

import com.bonyun.board.domain.role.Role;
import com.bonyun.board.domain.user.entity.User;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class UserDto {
    private String username;
    private String password;
    private Role role;

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(this.username)
                .password(passwordEncoder.encode(this.password))
                .build();
    }
}
