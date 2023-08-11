package com.example.wanted.board.application.service;

import com.example.wanted.board.adapter.in.web.dto.CreateBoardRequest;
import com.example.wanted.board.application.port.in.BoardUseCase;
import com.example.wanted.board.application.port.out.ChangeBoardPort;
import com.example.wanted.board.domain.Board;
import com.example.wanted.security.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService implements BoardUseCase {
    private final ChangeBoardPort changeBoardPort;
    @Override
    public void write(CreateBoardRequest request, UserInfo userInfo) {
        Board board = request.toDomain(userInfo.userId());

        changeBoardPort.save(board);
    }
}
