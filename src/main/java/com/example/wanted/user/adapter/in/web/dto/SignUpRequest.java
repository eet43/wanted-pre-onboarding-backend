package com.example.wanted.user.adapter.in.web.dto;

import com.example.wanted.common.response.CodeSet;
import com.example.wanted.common.response.CustomException;
import com.example.wanted.user.domain.User;

public record SignUpRequest(
        String email,
        String password
) {
    public User toDomain() {
        return User.builder()
                .email(this.email)
                .password(this.password)
                .build();
    }

    public void validate() {
        if (!this.email.contains("@")) {
            throw new CustomException(CodeSet.INVALID_EMAIL_FORMAT);
        } else if (this.password.replace(" ", "").length() < 8) {
            throw new CustomException(CodeSet.INVALID_PW_FORMAT);
        }
    };

}
