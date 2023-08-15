package com.example.wanted.user.application.port.service;

import com.example.wanted.common.response.CustomException;
import com.example.wanted.security.TokenProvider;
import com.example.wanted.user.adapter.in.web.dto.CustomUserDetails;
import com.example.wanted.user.adapter.in.web.dto.LoginRequest;
import com.example.wanted.user.adapter.in.web.dto.LoginToken;
import com.example.wanted.user.adapter.in.web.dto.SignUpRequest;
import com.example.wanted.user.application.port.in.AuthUseCase;
import com.example.wanted.user.application.port.out.ChangeUserPort;
import com.example.wanted.user.application.port.out.LoadUserPort;
import com.example.wanted.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {
    private final LoadUserPort loadUserPort;
    private final ChangeUserPort changeUserPort;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider jwtProvider;

    @Override
    @Transactional
    public void signUp(SignUpRequest request) {
        loadUserPort.checkEmail(request.email());

        User user = request.toDomain();
        user.encodePw(passwordEncoder);

        changeUserPort.save(user);
    }

    @Override
    public LoginToken login(LoginRequest request) {
        User findUser = loadUserPort.findUser(request.email());
        findUser.verifyPw(passwordEncoder, request.password());

        return createToken(findUser);
    }
    private LoginToken createToken(User findUser) {

        String accessToken = jwtProvider.generateAccessToken(findUser);
        String refreshToken = jwtProvider.generateRefreshToken(findUser);

        return new LoginToken(accessToken, refreshToken);
    }

}
