package com.example.wanted.user.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder
@Getter
public class User {
    private Long id;
    private String email;
    private String password;
    public void validate() {
        if (!this.email.contains("@")) {
            throw new IllegalArgumentException("제대로된 이메일 형식이 아닙니다.");
        } else if (this.password.replace(" ", "").length() < 8) {
            throw new IllegalArgumentException("비밀번호를 8자 이상으로 입력해주세요(공백 제거)");
        }
    };

    public void encodePw(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}