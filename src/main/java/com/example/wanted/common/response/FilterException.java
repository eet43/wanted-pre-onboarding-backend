package com.example.wanted.common.response;

import org.springframework.security.core.AuthenticationException;

public class FilterException extends AuthenticationException {
    public FilterException(CodeSet code) {
        super(code.getMessage());
        this.code = code;
    }
    private CodeSet code;

    public CodeSet getCode() {
        return this.code;
    }
}