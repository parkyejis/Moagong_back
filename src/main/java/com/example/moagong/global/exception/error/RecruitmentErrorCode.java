package com.example.moagong.global.exception.error;

import com.example.moagong.global.exception.model.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RecruitmentErrorCode implements BaseErrorCode {

    ALREADY_CREATED(HttpStatus.ALREADY_REPORTED, "RECRUITMENT_4090", "이미 모집공고를 생성하였습니다."),
    ALREADY_APPLY(HttpStatus.ALREADY_REPORTED, "RECRUITMENT_4090", "이미 신청한 모집 공고입니다."),
    APPLY_ALREADY_PROCESSED(HttpStatus.CONFLICT, "RECRUITMENT_4090", "이미 처리된 신청입니다."),
    ALREADY_MEMBER(HttpStatus.ALREADY_REPORTED, "RECRUITMENT_4090", "이미 가득찬 방입니다."),
    EXPIRED_RECRUITMENT(HttpStatus.ALREADY_REPORTED, "RECRUITMENT_4090","기간이 지난 공모전 입니다."),
    NOT_ALREADY_MEMBER(HttpStatus.ALREADY_REPORTED, "RECRUITMENT_4090", "인원이 부족한 방입니다."),
    NOT_FOUND_RECRUIT(HttpStatus.NOT_FOUND, "RECRUITMENT_4040", "해당 모집공고를 찾을 수 없습니다."),
    NO_PERMMISSION(HttpStatus.FORBIDDEN, "RECRUITMENT_4040","해당 권한이 존재하지 않습니다.");

    private final HttpStatus status;
    private final String message;
    private final String code;
}
