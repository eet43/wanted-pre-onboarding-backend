package com.example.wanted.board.adapter.in.web;

import com.example.wanted.board.adapter.in.web.dto.CreateBoardRequest;
import com.example.wanted.board.adapter.in.web.dto.CreateBoardResponse;
import com.example.wanted.board.adapter.in.web.dto.ModifyBoardRequest;
import com.example.wanted.board.application.port.in.BoardUseCase;
import com.example.wanted.board.domain.Board;
import com.example.wanted.common.response.CustomResponse;
import com.example.wanted.security.UserInfo;
import com.example.wanted.user.adapter.in.web.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardUseCase boardUseCase;

    @PostMapping()
    public CustomResponse write(@RequestBody CreateBoardRequest request, @AuthenticationPrincipal CustomUserDetails userInfo) {
        CreateBoardResponse data = boardUseCase.write(request, userInfo);
        return CustomResponse.success(data);
    }

    @GetMapping("/all")
    public CustomResponse<List<Board>> selectAll(@RequestParam Integer page, @RequestParam Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Board> data = boardUseCase.selectAll(pageable);

        return CustomResponse.success(data);
    }

    @GetMapping("/{boardId}")
    public CustomResponse<Board> selectAll(@PathVariable Long boardId) {
        Board data = boardUseCase.select(boardId);
        return CustomResponse.success(data);
    }
    @PutMapping("/{boardId}")
    public CustomResponse modify(@PathVariable Long boardId, @RequestBody ModifyBoardRequest request, @AuthenticationPrincipal CustomUserDetails userInfo) {
        boardUseCase.modify(boardId, request, userInfo);
        return CustomResponse.success();
    }

    @DeleteMapping("/{boardId}")
    public CustomResponse delete(@PathVariable Long boardId, @AuthenticationPrincipal CustomUserDetails userInfo) {
        boardUseCase.delete(boardId, userInfo);
        return CustomResponse.success();
    }
}
