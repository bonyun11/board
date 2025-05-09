package com.bonyun.board.domain.board.entity;

import com.bonyun.board.domain.board.dto.BoardDto;
import com.bonyun.board.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usersId", nullable = false)
    private User user;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "success")
    private boolean success;

    @CreatedDate
    @Column
    private LocalDateTime createdAt;

    // 업뎃 시간
    @LastModifiedDate
    @Column
    private LocalDateTime modifiedAt;

    public Board(BoardDto boardDto){
        this.title = boardDto.getTitle();
        this.content = boardDto.getContent();
        this.password =  boardDto.getPassword();
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }


    public void updateFromDto(BoardDto boardDto) {
        this.title = boardDto.getTitle(); // BoardDto에 title 필드가 있다고 가정
        this.content = boardDto.getContent(); // BoardDto에 content 필드가 있다고 가정
    }
}
