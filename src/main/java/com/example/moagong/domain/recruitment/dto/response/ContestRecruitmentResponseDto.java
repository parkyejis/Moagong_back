package com.example.moagong.domain.recruitment.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContestRecruitmentResponseDto {

    private final Long id;
    private final String title;
    private final String comment;

}
