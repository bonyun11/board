package com.bonyun.board.domain.board.controller;

import com.bonyun.board.domain.board.dto.BoardDto;
import com.bonyun.board.domain.board.dto.BoardPageResponseDto;
import com.bonyun.board.domain.board.dto.BoardResponseDto;
import com.bonyun.board.domain.board.service.BoardService;
import com.bonyun.board.domain.user.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 전체 게시물 조회
    @GetMapping
    public BoardPageResponseDto getPosts(
            //페이지네이션
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return boardService.getPosts(page, size);
    }

    //하나씩 조희
    @GetMapping("/{id}")
    public BoardResponseDto getPost(@PathVariable Long id) {
        return boardService.getPost(id);
    }

    // 게시물 생성
    @PostMapping
    public BoardResponseDto createPost(@RequestBody BoardDto boardDto) {
        return boardService.createPost(boardDto);
    }

    // 게시물 수정
    @PutMapping("/{id}")
    public BoardResponseDto updatePost(@PathVariable Long id, @RequestBody BoardDto boardDto) throws Exception {
        return boardService.updatePost(id, boardDto);
    }

    // 게시물 삭제
    @DeleteMapping("/{id}")
    public BoardResponseDto deletePost(@PathVariable Long id, @RequestBody BoardDto boardDto) throws Exception {
        return boardService.deletePost(id, boardDto);
    }

    // 좋아요 추가
    @PostMapping("/{id}/like")
    public BoardResponseDto addLike(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        return boardService.addLike(id, userDetails.getUsername());
    }

    // 좋아요 취소
    @DeleteMapping("/{id}/like")
    public BoardResponseDto removeLike(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        return boardService.removeLike(id, userDetails.getUsername());
    }
}