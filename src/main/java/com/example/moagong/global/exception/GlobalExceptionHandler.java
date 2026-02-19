package com.example.moagong.global.exception;

import com.example.moagong.global.exception.model.BaseErrorCode;
import com.example.moagong.global.response.BaseResponse;
import jakarta.persistence.ElementCollection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<BaseResponse<Void>> handleBadRequest(IllegalArgumentException e) {
//        return ResponseEntity.status(400)
//                .body(BaseResponse.error(400, e.getMessage()));
//    }
//
//    @ExceptionHandler(IllegalStateException.class)
//    public ResponseEntity<BaseResponse<Void>> handleIllegalState(IllegalStateException e) {
//        return ResponseEntity.status(401)
//                .body(BaseResponse.error(401, e.getMessage()));
//    }


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse<?>> handleCustomException(CustomException e) {
        BaseErrorCode baseErrorCode = e.getErrorCode();
        log.error("Custom 오류 발생 : {}",e.getErrorCode());
        return ResponseEntity
                .status(baseErrorCode.getStatus())
                .body(BaseResponse.error(baseErrorCode.getCode(), baseErrorCode.getMessage()));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<?>> handlerException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.error("500", e.getMessage()));
    }
}
