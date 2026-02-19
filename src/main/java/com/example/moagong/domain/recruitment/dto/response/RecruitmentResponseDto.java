package com.example.moagong.domain.recruitment.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RecruitmentResponseDto {
    private final String title;
    private final String master_id;
    private final String comment;
}
