package com.example.wanted.board.application.service;

import com.example.wanted.board.adapter.in.web.dto.CreateBoardRequest;
import com.example.wanted.board.application.port.in.BoardUseCase;
import com.example.wanted.board.application.port.out.ChangeBoardPort;
import com.example.wanted.board.domain.Board;
import com.example.wanted.security.UserInfo;
import com.example.wanted.user.adapter.in.web.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService implements BoardUseCase {
    private final ChangeBoardPort changeBoardPort;
    @Override
    public void write(CreateBoardRequest request, CustomUserDetails userInfo) {
        Board board = request.toDomain(userInfo.getId());

        changeBoardPort.save(board);
    }

    @Override
    public List<Board> selectAll(Pageable pageable) {
        return null;
    }
}
