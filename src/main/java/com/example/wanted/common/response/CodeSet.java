package com.example.wanted.common.response;

import lombok.Getter;

@Getter
public enum CodeSet {
    OK("0000", "OK"),
    INVALID_EMAIL_FORMAT("1001", "제대로된 이메일 형식이 아닙니다."),
    INVALID_PW_FORMAT("1002", "비밀번호를 8자 이상으로 입력해주세요(공백 제거)"),
    INVALID_EMAIL("1003", "해당 이메일의 유저를 찾을 수 없습니다."),
    INVALID_PASSWORD("1004", "비밀번호가 틀렸습니다."),
    ALREADY_EMAIL("1005", "이미 존재하는 이메일입니다.");

    private final String code;
    private final String message;

    CodeSet(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
