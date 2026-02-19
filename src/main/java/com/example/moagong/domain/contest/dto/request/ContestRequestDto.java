package com.example.moagong.domain.contest.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ContestRequestDto {

    @NotBlank
    private final String title;

    private LocalDate start;
    private LocalDate end;

    @NotBlank
    private String host;      //주최기관

    @NotBlank
    private String study;      //전공/분야

    @NotBlank
    private String explanation; //설명
}
