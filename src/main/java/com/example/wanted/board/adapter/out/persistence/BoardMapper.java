package com.example.wanted.board.adapter.out.persistence;

import com.example.wanted.board.domain.Board;
import com.example.wanted.user.adapter.out.persistence.UserEntity;
import com.example.wanted.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class BoardMapper {

    public Board toDomain(BoardEntity entity) {
        return Board.builder()
                .id(entity.getId())
                .writerId(entity.getWriterId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .createdTime(entity.getCreatedTime())
                .modifiedTime(entity.getModifiedTime())
                .build();
    }

    public BoardEntity toEntity(Board domain) {
        return BoardEntity.builder()
                .id(domain.getId())
                .writerId(domain.getWriterId())
                .title(domain.getTitle())
                .content(domain.getContent())
                .createdTime(domain.getCreatedTime())
                .modifiedTime(domain.getModifiedTime())
                .build();
    }
}
