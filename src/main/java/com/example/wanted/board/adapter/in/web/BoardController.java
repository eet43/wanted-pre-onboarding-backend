package com.example.wanted.board.adapter.in.web;

import com.example.wanted.board.adapter.in.web.dto.CreateBoardRequest;
import com.example.wanted.board.application.port.in.BoardUseCase;
import com.example.wanted.common.response.CustomResponse;
import com.example.wanted.security.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardUseCase boardUseCase;

    @PostMapping()
    public CustomResponse write(@RequestBody CreateBoardRequest request, @AuthenticationPrincipal UserInfo userInfo) {
        boardUseCase.write(request, userInfo);
        return CustomResponse.success();
    }
}
