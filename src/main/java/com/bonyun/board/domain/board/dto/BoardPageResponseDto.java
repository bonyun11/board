package com.bonyun.board.domain.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BoardPageResponseDto {
    private List<BoardResponseDto> posts;
    private int totalPages;
    private int currentPage;
    private int pageSize;
    private long totalElements;

    public BoardPageResponseDto(List<BoardResponseDto> posts, int totalPages, int currentPage, int pageSize, long totalElements) {
        this.posts = posts;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
    }
}