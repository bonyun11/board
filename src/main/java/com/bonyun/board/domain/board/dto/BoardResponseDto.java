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

    public BoardResponseDto(Board board){
        this.id = board.getId();
        this.user = board.getUser();
        this.title = board.getTitle();
        this.content = board.getContent();
    }

}
