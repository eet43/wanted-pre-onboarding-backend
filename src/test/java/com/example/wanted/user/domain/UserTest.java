package com.example.wanted.user.domain;

import com.example.wanted.common.response.CodeSet;
import com.example.wanted.common.response.CustomException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class UserTest {
    @Test
    void 유효성_테스트() throws Exception {
        // 유효한 값
        User user1 = User.builder()
                .email("1234@gmail.com")
                .password("121345678")
                .build();
        assertDoesNotThrow(user1::validate);

        User user2 = User.builder()
                .email("1234gmail.com")
                .password("121345678")
                .build();
        assertThatThrownBy(user2::validate)
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("code", CodeSet.INVALID_EMAIL_FORMAT);

        User user3 = User.builder()
                .email("1234@gmail.com")
                .password("12134")
                .build();
        assertThatThrownBy(user3::validate)
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("code", CodeSet.INVALID_PW_FORMAT);
    }

    @Test
    void 인코딩_테스트() throws Exception {
        //given
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = User.builder()
                .password("121345678")
                .build();

        //when
        user.encodePw(passwordEncoder);

        //then
        assertTrue(passwordEncoder.matches("121345678", user.getPassword()));
    }

    @Test
    void 비밀번호_확인() throws Exception {
        //given
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = User.builder()
                .password("12345678")
                .build();
        user.encodePw(passwordEncoder);

        assertDoesNotThrow(() -> user.verifyPw(passwordEncoder, "12345678"));
        assertThatThrownBy(() -> user.verifyPw(passwordEncoder, "11111111"))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("code", CodeSet.INVALID_PASSWORD);

    }
}