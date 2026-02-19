package com.example.moagong.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseResponse<T> {

    private boolean success;

    private String code;

    private String message;

    private T data;

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(true, "200", "요청이 성공적으로 처리되었습니다.", data);
    }

    public static <T> BaseResponse<T> success(String code, String message, T data){
        return new BaseResponse<>(true, code, message, data);
    }

    public static <T> BaseResponse<T> error(String code, String message){
        return new BaseResponse<>(false, code, message, null);
    }
}
