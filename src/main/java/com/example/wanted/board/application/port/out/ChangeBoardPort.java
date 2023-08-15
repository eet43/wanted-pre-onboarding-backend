package com.example.wanted.board.application.port.out;

import com.example.wanted.board.domain.Board;

public interface ChangeBoardPort {
    Long save(Board board);
    void delete(Board board);
}
