package com.example.wanted.board.application.port.out;

import com.example.wanted.board.domain.Board;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LoadBoardPort {
    List<Board> findAll(Pageable pageable);
    Board findOne(Long boardId);
}
