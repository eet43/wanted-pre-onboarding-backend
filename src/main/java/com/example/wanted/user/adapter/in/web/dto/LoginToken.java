package com.example.wanted.user.adapter.in.web.dto;

public record LoginToken(
        String accessToken,
        String refreshToken
) {
}
