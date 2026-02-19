package com.example.moagong.global.exception;

import com.example.moagong.global.exception.model.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
//전역적으로 사용될 에러들을 선언해 놓은 enum 클래스
public enum GlobalErrorCode implements BaseErrorCode {
    INVALID_INPUT_VALUE("G_001", "유효하지 않은 입력입니다.", HttpStatus.BAD_REQUEST), //각각의 변수가 필드 변수로 값에 들어가게 된다.
    RESOURCE_NOT_FOUND("G_002", "요청한 리소스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR("G_003", "서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    PAGE_SORT_STYLE_WRONG("G_004", "Page Sort 설정 양식이 잘못되었습니다.", HttpStatus.BAD_REQUEST);

    private final String code;  //응답 코드
    private final String message;   //응답 메시지
    private final HttpStatus status;    //http 상태

}
