package com.example.moagong.global.exception.model;

import org.springframework.http.HttpStatus;

//각 도메인에서 발생할 수 있는 에러들을 담기위한 커스텀 에러 코드의 부모 인터페이스
public interface BaseErrorCode {
    String getCode();
    String getMessage();
    HttpStatus getStatus();
}


/*
* 모든 에러 코드는 enum이 따라야하는 메서드 규칙을 정의한다 .
* enum에서 전달하는 값이 code, message, status 를 각각 전달하는데 규격에 맞춰야한다
* 도메인마다 에러를 enum으로 만들으면 UserErrorCode, ApplicationErrorCode, VlockErrorCode 이렇게 여러개 생기는 문제가 발생
* -> 전역 핸들러이기 때문에 어떤 도메인의 에러든 동일한 방식으로 응답을 만들어야 함
* -> 모든 에러 코드는 enum이 반드시 제공해야 하는 정보인
*       HTTP status (400/404/403/500 등)
*       내부 code
*       message
*       를 인터페이스로 강제한다 */