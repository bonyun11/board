package com.bonyun.board.domain.comment.controiier;

import com.bonyun.board.domain.comment.dto.CommentDto;
import com.bonyun.board.domain.comment.dto.CommentResponseDto;
import com.bonyun.board.domain.comment.service.CommentService;
import com.bonyun.board.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards/{boardId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글/대댓글 생성
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long boardId,
            @RequestBody CommentDto commentDto,
            @AuthenticationPrincipal User user) {
        if (user == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        CommentResponseDto response = commentService.createComment(boardId, commentDto, user);
        return ResponseEntity.ok(response);
    }

    // 댓글 목록 조회
    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long boardId) {
        List<CommentResponseDto> comments = commentService.getComments(boardId);
        return ResponseEntity.ok(comments);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentDto commentDto) {
        CommentResponseDto response = commentService.updateComment(commentId, commentDto);
        return ResponseEntity.ok(response);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @RequestBody CommentDto commentDto) {
        commentService.deleteComment(commentId, commentDto.getPassword());
        return ResponseEntity.noContent().build();
    }
}
