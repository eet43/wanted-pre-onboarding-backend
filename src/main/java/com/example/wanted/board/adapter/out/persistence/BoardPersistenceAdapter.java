package com.example.wanted.board.adapter.out.persistence;

import com.example.wanted.board.application.port.out.ChangeBoardPort;
import com.example.wanted.board.application.port.out.LoadBoardPort;
import com.example.wanted.board.domain.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BoardPersistenceAdapter implements LoadBoardPort, ChangeBoardPort {

    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;
    @Override
    public void save(Board board) {
        BoardEntity entity = boardMapper.toEntity(board);

        boardRepository.save(entity);
    }

    @Override
    public List<Board> findAll(Pageable pageable) {
        List<BoardEntity> findEntities = boardRepository.findAll(pageable).getContent();
        return findEntities.stream()
                .map(boardMapper::toDomain)
                .toList();
    }
}
