package com.example.moagong.domain.recruitment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ApplyRequestDto {

    private final String introduce;
}
