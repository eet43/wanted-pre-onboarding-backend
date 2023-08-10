package com.example.wanted.common.response;

import lombok.Getter;

@Getter
public enum CodeSet {
    OK("0000", "OK"),
    INVALID_EMAIL_FORMAT("1001", "제대로된 이메일 형식이 아닙니다."),
    INVALID_PW_FORMAT("1002", "비밀번호를 8자 이상으로 입력해주세요(공백 제거)");

    private final String code;
    private final String message;

    CodeSet(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
