package com.bonyun.board.domain.board.dto;

import com.bonyun.board.domain.board.entity.Board;
import com.bonyun.board.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardResponseDto {
    private Long id;
    private User user;
    private String title;
    private String content;
    private Long viewCount; // 조회수 필드 추가
    private Long likeCount; // 좋아요 필드 추가

    public BoardResponseDto(Board board){
        this.id = board.getId();
        this.user = board.getUser();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.viewCount = board.getViewCount(); // 조회수 설정
        this.likeCount = board.getLikeCount(); // 좋아요 설정
    }

}
