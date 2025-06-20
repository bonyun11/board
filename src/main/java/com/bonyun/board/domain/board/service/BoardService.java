package com.bonyun.board.domain.board.service;


import com.bonyun.board.domain.board.dto.BoardDto;
import com.bonyun.board.domain.board.dto.BoardPageResponseDto;
import com.bonyun.board.domain.board.dto.BoardResponseDto;
import com.bonyun.board.domain.board.entity.Board;
import com.bonyun.board.domain.board.entity.Like;
import com.bonyun.board.domain.board.repository.BoardRepository;
import com.bonyun.board.domain.board.repository.LikeRepository;
import com.bonyun.board.domain.user.entity.User;
import com.bonyun.board.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    //게시글 생성
    public BoardResponseDto createPost(BoardDto boardDto) {
        User user = userRepository.findByUsername(boardDto.getUser()).orElseThrow(
                () -> new RuntimeException("user not found"));

        Board newBoard = boardRepository.save(boardDto.toEntity(user));
        return new BoardResponseDto(newBoard);
    }

    //전체 게시글 조회
    @Transactional(readOnly = true)
    public BoardPageResponseDto getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("modifiedAt").descending());
        Page<Board> boardPage = boardRepository.findAll(pageable);
        return new BoardPageResponseDto(
                boardPage.getContent().stream().map(BoardResponseDto::new).toList(),
                boardPage.getTotalPages(),
                boardPage.getNumber(),
                boardPage.getSize(),
                boardPage.getTotalElements()
        );
    }

    //하나씩 조회
    @Transactional
    public BoardResponseDto getPost(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        board.increaseViewCount(); // 조회수 증가
        boardRepository.save(board); // 변경사항 저장
        return new BoardResponseDto(board);
    }

    //게시글 수정
    public BoardResponseDto updatePost(Long id, BoardDto boardDto) throws Exception {
        Board newBoard = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if (!boardDto.getPassword().equals(newBoard.getPassword())) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        // 기존 Board 객체의 필드를 BoardDto 값으로 업데이트
        newBoard.updateFromDto(boardDto); // Board 클래스에 이 메서드를 추가해야 함
        boardRepository.save(newBoard);

        return new BoardResponseDto(newBoard);
    }

    //게시글 삭제
    public BoardResponseDto deletePost(Long id, BoardDto boardDto) throws Exception{
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if (!boardDto.getPassword().equals(board.getPassword()))
            throw new Exception("비밀번호가 일치하지 않습니다.");

        boardRepository.deleteById(id);
        return new BoardResponseDto(board);
    }

    // 좋아요 추가
    @Transactional
    public BoardResponseDto addLike(Long boardId, String username) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 ID가 존재하지 않습니다."));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 중복 좋아요 체크
        likeRepository.findByBoardAndUser(board, user)
                .ifPresent(like -> { throw new IllegalStateException("이미 좋아요를 눌렀습니다."); });

        // 좋아요 추가
        Like like = Like.builder()
                .board(board)
                .user(user)
                .build();
        likeRepository.save(like);

        // 게시글 좋아요 수 증가
        board.incrementLikeCount();
        boardRepository.save(board);

        return new BoardResponseDto(board);
    }

    // 좋아요 취소
    @Transactional
    public BoardResponseDto removeLike(Long boardId, String username) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 ID가 존재하지 않습니다."));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 좋아요 존재 여부 체크
        Like like = likeRepository.findByBoardAndUser(board, user)
                .orElseThrow(() -> new IllegalStateException("좋아요를 누르지 않았습니다."));

        // 좋아요 삭제
        likeRepository.delete(like);

        // 게시글 좋아요 수 감소
        board.decreaseLikeCount();
        boardRepository.save(board);

        return new BoardResponseDto(board);
    }

}