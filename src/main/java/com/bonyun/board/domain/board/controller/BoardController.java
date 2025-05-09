package com.bonyun.board.domain.board.controller;

import com.bonyun.board.domain.board.dto.BoardDto;
import com.bonyun.board.domain.board.dto.BoardResponseDto;
import com.bonyun.board.domain.board.service.BoardService;
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
    public List<BoardResponseDto> getPosts() {
        return boardService.getPosts();
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
}