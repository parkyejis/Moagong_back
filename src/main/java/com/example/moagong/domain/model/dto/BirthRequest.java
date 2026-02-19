package com.example.moagong.domain.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BirthRequest {
    @NotBlank
    private final Long year;

    @NotBlank
    private final Long month;

    @NotBlank
    private final Long day;
}
