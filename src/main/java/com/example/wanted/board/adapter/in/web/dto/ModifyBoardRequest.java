package com.example.wanted.board.adapter.in.web.dto;

import lombok.Getter;
public record ModifyBoardRequest(
        String title,
        String content
) {

}
