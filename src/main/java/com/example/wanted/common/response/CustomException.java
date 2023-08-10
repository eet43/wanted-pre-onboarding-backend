package com.example.wanted.common.response;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private CodeSet code;

    public CustomException(CodeSet code) {
        this.code = code;
    }
}
