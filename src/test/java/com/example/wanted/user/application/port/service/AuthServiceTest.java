package com.example.wanted.user.application.port.service;

import com.example.wanted.user.adapter.in.web.dto.SignUpRequest;
import com.example.wanted.user.application.port.out.ChangeUserPort;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@Import({ChangeUserPort.class, PasswordEncoder.class, AuthService.class})
class AuthServiceTest {
    @MockBean
    ChangeUserPort changeUserPort;

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

}