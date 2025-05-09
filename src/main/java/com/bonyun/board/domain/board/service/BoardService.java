package com.bonyun.board.domain.board.service;


import com.bonyun.board.domain.board.dto.BoardDto;
import com.bonyun.board.domain.board.dto.BoardResponseDto;
import com.bonyun.board.domain.board.entity.Board;
import com.bonyun.board.domain.board.repository.BoardRepository;
import com.bonyun.board.domain.user.entity.User;
import com.bonyun.board.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    //게시글 생성
    public BoardResponseDto createPost(BoardDto boardDto) {
        User user = userRepository.findByUsername(boardDto.getUser()).orElseThrow(
                () -> new RuntimeException("user not found"));

        Board newBoard = boardRepository.save(boardDto.toEntity(user));
        return new BoardResponseDto(newBoard);
    }

    //전체 게시글 조회
    public List<BoardResponseDto> getPosts(){
        return boardRepository.findAllByOrderByModifiedAtDesc().stream().map(BoardResponseDto::new).toList();
    }

    //하나씩 조회
    public BoardResponseDto getPost(Long id) {
        return boardRepository.findById(id).map(BoardResponseDto::new).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
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


}