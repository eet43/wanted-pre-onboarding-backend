package com.example.wanted.board.application.port.in;

import com.example.wanted.board.adapter.in.web.dto.CreateBoardRequest;
import com.example.wanted.security.UserInfo;

public interface BoardUseCase {
    void write(CreateBoardRequest request, UserInfo userInfo);
}
