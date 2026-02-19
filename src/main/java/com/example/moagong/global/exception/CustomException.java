package com.example.moagong.global.exception;

import com.example.moagong.global.exception.model.BaseErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private final BaseErrorCode errorCode;

    public CustomException(BaseErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
