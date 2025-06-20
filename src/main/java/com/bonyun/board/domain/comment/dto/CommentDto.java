package com.bonyun.board.domain.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
    private String content;
    private String password;
    private Long parentCommentId; // 대댓글인 경우 부모 댓글 ID
}
