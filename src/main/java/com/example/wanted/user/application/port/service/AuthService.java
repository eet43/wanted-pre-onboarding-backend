package com.example.wanted.user.application.port.service;

import com.example.wanted.user.adapter.in.web.dto.LoginRequest;
import com.example.wanted.user.adapter.in.web.dto.SignUpRequest;
import com.example.wanted.user.application.port.in.AuthUseCase;
import com.example.wanted.user.application.port.out.ChangeUserPort;
import com.example.wanted.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {
    private final ChangeUserPort changeUserPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(SignUpRequest request) {
        User user = request.toDomain();
        user.validate();
        user.encodePw(passwordEncoder);

        changeUserPort.save(user);
    }

    @Override
    public void login(LoginRequest request) {

    }
}
