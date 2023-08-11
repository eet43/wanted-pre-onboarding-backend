package com.example.wanted.board.application.port.out;

import com.example.wanted.board.domain.Board;

public interface ChangeBoardPort {
    void save(Board board);
}
