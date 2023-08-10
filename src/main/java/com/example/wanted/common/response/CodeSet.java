package com.example.wanted.common.response;

import lombok.Getter;

@Getter
public enum CodeSet {
    OK("0000", "OK");

    private final String code;
    private final String message;

    CodeSet(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
