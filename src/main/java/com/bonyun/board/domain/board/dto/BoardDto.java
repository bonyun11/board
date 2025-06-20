package com.bonyun.board.domain.board.dto;

import com.bonyun.board.domain.board.entity.Board;
import com.bonyun.board.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardDto {
    private String user;
    private String title;
    private String password;
    private String content;

    public Board toEntity(User user){
        return Board.builder()
                .user(user)
                .title(this.title)
                .password(this.password)
                .content(this.content)
                .build();
    }


}
