package com.bonyun.board.domain.user.entity;

import com.bonyun.board.domain.role.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Builder
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // JPA에서 기본 생성자 필요, 외부 접근 차단
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Getter
    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Getter
    @Enumerated(EnumType.STRING)
    private Role role;
}
