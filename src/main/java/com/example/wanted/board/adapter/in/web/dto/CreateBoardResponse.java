package com.example.wanted.board.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateBoardResponse(
        @Schema(description = "게시글 Id", nullable = false, example = "1L")
        Long boardId
) {
}
