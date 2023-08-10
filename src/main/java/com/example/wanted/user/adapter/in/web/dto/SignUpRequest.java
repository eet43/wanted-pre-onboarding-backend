package com.example.wanted.user.adapter.in.web.dto;

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

}
