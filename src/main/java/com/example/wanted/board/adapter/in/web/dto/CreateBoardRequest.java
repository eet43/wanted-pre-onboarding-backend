package com.example.wanted.board.adapter.in.web.dto;

import com.example.wanted.board.domain.Board;

import java.time.LocalDateTime;

public record CreateBoardRequest(
        String title,
        String content
) {
    public Board toDomain(Long writerId) {
        return Board.builder()
                .writerId(writerId)
                .title(title)
                .content(content)
                .createdTime(LocalDateTime.now())
                .modifiedTime(LocalDateTime.now())
                .build();
    }
}
