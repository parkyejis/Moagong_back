package com.example.moagong.domain.recruitment.dto.request;

import jakarta.persistence.Column;
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

    private final int count;

}
