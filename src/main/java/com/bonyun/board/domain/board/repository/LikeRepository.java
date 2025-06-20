package com.bonyun.board.domain.board.repository;

import com.bonyun.board.domain.board.entity.Board;
import com.bonyun.board.domain.board.entity.Like;
import com.bonyun.board.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {
    Optional<Like> findByBoardAndUser(Board board, User user);
    void deleteByBoardAndUser(Board board, User user);
}
