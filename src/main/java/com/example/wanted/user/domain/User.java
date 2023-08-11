package com.example.wanted.user.domain;

import com.example.wanted.common.response.CodeSet;
import com.example.wanted.common.response.CustomException;
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
            throw new CustomException(CodeSet.INVALID_EMAIL_FORMAT);
        } else if (this.password.replace(" ", "").length() < 8) {
            throw new CustomException(CodeSet.INVALID_PW_FORMAT);
        }
    };
    public void encodePw(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
    public void verifyPw(PasswordEncoder passwordEncoder, String password) {
        if(!passwordEncoder.matches(password, this.password)) {
            throw new CustomException(CodeSet.INVALID_PASSWORD);
        }
    }
}