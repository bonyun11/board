package com.bonyun.board.domain.comment.dto;

import com.bonyun.board.domain.comment.entity.Comment;
import com.bonyun.board.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private User user;
    private String content;
    private Long parentCommentId;
    private List<CommentResponseDto> replies = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.user = comment.getUser();
        this.content = comment.getContent();
        this.parentCommentId = comment.getParentComment() != null ? comment.getParentComment().getId() : null;
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.replies = comment.getReplies().stream()
                .map(CommentResponseDto::new)
                .toList();
    }
}
