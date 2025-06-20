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

    @Column(name = "view_count", nullable = false)
    @Builder.Default
    private Long viewCount = 0L; // 조회수 필드 추가, 기본값 0

    @Column(name = "like_count", nullable = false)
    @Builder.Default
    private Long likeCount = 0L; // 좋아요 필드 추가, 기본값 0

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
        this.viewCount = 0L;
        this.likeCount = 0L;
    }


    public void updateFromDto(BoardDto boardDto) {
        this.title = boardDto.getTitle(); // BoardDto에 title 필드가 있다고 가정
        this.content = boardDto.getContent(); // BoardDto에 content 필드가 있다고 가정
    }

    public void increaseViewCount() {
        this.viewCount++; // 조회수 증가
    }

    public void incrementLikeCount() {
        this.likeCount++; // 좋아요 증가
    }

    public void decreaseLikeCount() {
        if (this.likeCount > 0) { // 좋아요 감소
            this.likeCount--;
        }
    }

}
