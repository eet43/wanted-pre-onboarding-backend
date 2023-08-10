package com.example.wanted.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"code", "message", "result"})  /** Json 으로 나갈 순서를 설정하는 어노테이션*/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomResponse<T> {
    @JsonProperty("code")
    private final String code;
    private final String message;
    private T result;

    private CustomResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private CustomResponse(String code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    /** 1. [API 성공시 나가는 응답] */
    public static CustomResponse success(){
        return new CustomResponse(CodeSet.OK.getCode(), CodeSet.OK.getMessage());
    }

    public static <T> CustomResponse<T> success(T result){
        return new CustomResponse<>(CodeSet.OK.getCode(), CodeSet.OK.getMessage(), result);
    }

    /** 2. [API 실패시 나가는 응답] */
    public static CustomResponse fail(CodeSet code){
        return new CustomResponse<>(code.getCode(), code.getMessage());
    }

}
