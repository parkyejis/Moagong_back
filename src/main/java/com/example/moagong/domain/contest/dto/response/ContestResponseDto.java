package com.example.moagong.domain.contest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class ContestResponseDto {
    private final String title;
    private final LocalDate start;
    private LocalDate end;
    private String host;      //주최기관
    private String study;      //전공/분야
    private String explanation; //설명
}
