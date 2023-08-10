package com.example.wanted.user.application.port.in;

import com.example.wanted.user.adapter.in.web.dto.LoginRequest;
import com.example.wanted.user.adapter.in.web.dto.SignUpRequest;

public interface AuthUseCase {
    void signUp(SignUpRequest request);
    void login(LoginRequest request);
}
