package com.example.wanted.user.adapter.in.web;

import com.example.wanted.common.response.CustomResponse;
import com.example.wanted.user.adapter.in.web.dto.LoginRequest;
import com.example.wanted.user.adapter.in.web.dto.LoginToken;
import com.example.wanted.user.adapter.in.web.dto.SignUpRequest;
import com.example.wanted.user.application.port.in.AuthUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping("/sign")
    public CustomResponse signUp(@RequestBody SignUpRequest request) {
        request.validate();
        authUseCase.signUp(request);

        return CustomResponse.success();
    }

    @PostMapping("/login")
    public CustomResponse<LoginToken> login(@RequestBody LoginRequest request) {
        request.validate();
        LoginToken token = authUseCase.login(request);

        return CustomResponse.success(token);
    }

}
