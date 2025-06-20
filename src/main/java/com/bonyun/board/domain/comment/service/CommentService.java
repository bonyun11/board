package com.bonyun.board.domain.comment.service;

import com.bonyun.board.domain.board.entity.Board;
import com.bonyun.board.domain.board.repository.BoardRepository;
import com.bonyun.board.domain.comment.dto.CommentDto;
import com.bonyun.board.domain.comment.dto.CommentResponseDto;
import com.bonyun.board.domain.comment.entity.Comment;
import com.bonyun.board.domain.comment.repository.CommentRepository;
import com.bonyun.board.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    // 댓글/대댓글 생성
    @Transactional
    public CommentResponseDto createComment(Long boardId, CommentDto commentDto, User user) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 ID가 존재하지 않습니다."));

        Comment comment = Comment.builder()
                .board(board)
                .user(user)
                .content(commentDto.getContent())
                .password(commentDto.getPassword())
                .build();

        if (commentDto.getParentCommentId() != null) {
            Comment parentComment = commentRepository.findById(commentDto.getParentCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글 ID가 존재하지 않습니다."));
            if (parentComment.getParentComment() != null) {
                throw new IllegalArgumentException("대댓글에는 대댓글을 달 수 없습니다.");
            }
            comment.setParentComment(parentComment);
        }

        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    // 게시글의 댓글 목록 조회
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getComments(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 ID가 존재하지 않습니다."));
        return commentRepository.findByBoardAndParentCommentIsNull(board)
                .stream()
                .map(CommentResponseDto::new)
                .toList();
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentDto commentDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 ID가 존재하지 않습니다."));
        if (!commentDto.getPassword().equals(comment.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        comment.updateContent(commentDto.getContent());
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId, String password) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 ID가 존재하지 않습니다."));
        if (!password.equals(comment.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        commentRepository.delete(comment);
    }
}