package com.example.wanted.board.adapter.out.persistence;

import com.example.wanted.board.application.port.out.ChangeBoardPort;
import com.example.wanted.board.domain.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BoardPersistenceAdapter implements ChangeBoardPort {

    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;
    @Override
    public void save(Board board) {
        BoardEntity entity = boardMapper.toEntity(board);

        boardRepository.save(entity);
    }
}
