package com.bonyun.board.domain.user.repository;

import com.bonyun.board.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);

    boolean existsByUsername(String username);
    boolean existsByPassword(String password);
    boolean existsById(Long id);
}
