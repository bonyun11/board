package com.bonyun.board.domain.comment.repository;

import com.bonyun.board.domain.comment.entity.Comment;
import com.bonyun.board.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoardAndParentCommentIsNull(Board board);
}
