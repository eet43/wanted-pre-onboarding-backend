package com.example.wanted.board.domain;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class Board {
    private Long id;
    private Long writerId;
    private String title;
    private String content;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;

    public void modify(String title, String content) {
        this.title = title;
        this.content = content;
        this.modifiedTime = LocalDateTime.now();
    }
}
