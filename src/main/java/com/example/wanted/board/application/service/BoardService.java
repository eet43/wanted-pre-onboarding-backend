package com.example.wanted.board.application.service;

import com.example.wanted.board.adapter.in.web.dto.CreateBoardRequest;
import com.example.wanted.board.adapter.in.web.dto.CreateBoardResponse;
import com.example.wanted.board.adapter.in.web.dto.ModifyBoardRequest;
import com.example.wanted.board.application.port.in.BoardUseCase;
import com.example.wanted.board.application.port.out.ChangeBoardPort;
import com.example.wanted.board.application.port.out.LoadBoardPort;
import com.example.wanted.board.domain.Board;
import com.example.wanted.common.response.CodeSet;
import com.example.wanted.common.response.CustomException;
import com.example.wanted.user.adapter.in.web.dto.CustomUserDetails;
import com.example.wanted.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService implements BoardUseCase {
    private final LoadBoardPort loadBoardPort;
    private final ChangeBoardPort changeBoardPort;
    @Override
    @Transactional
    public CreateBoardResponse write(CreateBoardRequest request, CustomUserDetails userInfo) {
        Board board = request.toDomain(userInfo.getUser().getId());

        Long boardId = changeBoardPort.save(board);
        return new CreateBoardResponse(boardId);
    }
    @Override
    public List<Board> selectAll(Pageable pageable) {
        return loadBoardPort.findAll(pageable);
    }

    @Override
    public Board select(Long boardId) {
        return loadBoardPort.findOne(boardId);
    }

    @Override
    @Transactional
    public void modify(Long boardId, ModifyBoardRequest request, CustomUserDetails userInfo) {
        User accessUser = userInfo.getUser();
        Board board = loadBoardPort.findOne(boardId);

        checkAccess(accessUser.getId(), board.getWriterId());
        board.modify(request.title(), request.content());

        changeBoardPort.save(board);
    }

    @Override
    @Transactional
    public void delete(Long boardId, CustomUserDetails userInfo) {
        User accessUser = userInfo.getUser();
        Board board = loadBoardPort.findOne(boardId);

        checkAccess(accessUser.getId(), board.getWriterId());
        changeBoardPort.delete(board);
    }

    private void checkAccess(Long userId, Long writerId) {
        if (!writerId.equals(userId)) {
            throw new CustomException(CodeSet.INVALID_ACCESS);
        }
    }
}
