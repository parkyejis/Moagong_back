package com.example.moagong.domain.recruitment.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class RecruitmentRequestDto {

    @NotBlank
    private final String title;

    @NotBlank
    private final String comment;

    @Min(value=2, message ="팀원은 최소 2명입니다.")
    @Max(value = 6, message ="팀원은 최대 6명 입니다.")
    private final int count;

}
