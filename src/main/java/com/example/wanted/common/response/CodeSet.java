package com.example.wanted.common.response;

import lombok.Getter;

@Getter
public enum CodeSet {
    OK("0000", "OK"),
    INVALID_EMAIL_FORMAT("1001", "제대로된 이메일 형식이 아닙니다."),
    INVALID_PW_FORMAT("1002", "비밀번호를 8자 이상으로 입력해주세요(공백 제거)"),
    INVALID_EMAIL("1003", "해당 이메일의 유저를 찾을 수 없습니다."),
    INVALID_PASSWORD("1004", "비밀번호가 틀렸습니다."),
    ALREADY_EMAIL("1005", "이미 존재하는 이메일입니다."),
    INVALID_TOKEN("1006", "유효하지 않은 토큰입니다."),
    INVALID_SIGNATURE("1007", "잘못된 JWT 서명입니다."),
    EXPIRED_TOKEN("1008", "만료된 JWT 토큰입니다."),
    UNSUPPORTED_TOKEN("1009", "지원되지 않는 JWT 토큰입니다."),
    INVALID_BOARD("2001", "해당 게시글을 찾을 수 없습니다."),
    INVALID_ACCESS("2002", "게시글에 대한 권한이 없습니다.");

    private final String code;
    private final String message;

    CodeSet(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
