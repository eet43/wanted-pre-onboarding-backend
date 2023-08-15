package com.example.wanted.user.application.port.service;

import com.example.wanted.security.TokenProvider;
import com.example.wanted.user.adapter.in.web.dto.LoginRequest;
import com.example.wanted.user.adapter.in.web.dto.LoginToken;
import com.example.wanted.user.adapter.in.web.dto.SignUpRequest;
import com.example.wanted.user.application.port.out.ChangeUserPort;
import com.example.wanted.user.application.port.out.LoadUserPort;
import com.example.wanted.user.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import({ChangeUserPort.class, LoadUserPort.class, TokenProvider.class, PasswordEncoder.class, AuthService.class})
class AuthServiceTest {
    @MockBean
    ChangeUserPort changeUserPort;

    @MockBean
    LoadUserPort loadUserPort;

    @MockBean
    TokenProvider jwtProvider;

    @MockBean
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthService authService;

    @Test
    void 회원가입_호출() throws Exception {
        //given
        SignUpRequest request = new SignUpRequest("1234@gmail.com", "12345678");

        //when
        authService.signUp(request);

        //then
        verify(changeUserPort, times(1)).save(any(User.class));
    }

    @Test
    void 로그인_호출() throws Exception {
        //given
        LoginRequest request = new LoginRequest("1234@gmail.com", "12345678");
        User user = User.builder()
                .email("1234@gmail.com")
                .password("12345678")
                .build();

        when(loadUserPort.findUser(request.email())).thenReturn(user);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtProvider.generateAccessToken(user)).thenReturn("accessToken");
        when(jwtProvider.generateRefreshToken(user)).thenReturn("refreshToken");

        //when
        LoginToken loginToken = authService.login(request);

        //then
        assertEquals("accessToken", loginToken.accessToken());
        assertEquals("refreshToken", loginToken.refreshToken());
    }

}