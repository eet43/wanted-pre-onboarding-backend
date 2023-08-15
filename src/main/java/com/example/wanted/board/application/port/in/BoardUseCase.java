package com.example.wanted.board.application.port.in;

import com.example.wanted.board.adapter.in.web.dto.CreateBoardRequest;
import com.example.wanted.board.adapter.in.web.dto.CreateBoardResponse;
import com.example.wanted.board.adapter.in.web.dto.ModifyBoardRequest;
import com.example.wanted.board.domain.Board;
import com.example.wanted.user.adapter.in.web.dto.CustomUserDetails;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardUseCase {
    CreateBoardResponse write(CreateBoardRequest request, CustomUserDetails userInfo);
    List<Board> selectAll(Pageable pageable);
    Board select(Long boardId);
    void modify(Long boardId, ModifyBoardRequest request, CustomUserDetails userInfo);
    void delete(Long boardId, CustomUserDetails userInfo);
}
