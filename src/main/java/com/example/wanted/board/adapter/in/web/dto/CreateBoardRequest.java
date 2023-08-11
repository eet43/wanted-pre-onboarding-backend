package com.example.wanted.board.adapter.in.web.dto;

import com.example.wanted.board.domain.Board;

public record CreateBoardRequest(
        String title,
        String content
) {
    public Board toDomain(Long writerId) {
        return Board.builder()
                .writerId(writerId)
                .title(title)
                .content(content)
                .build();
    }
}
