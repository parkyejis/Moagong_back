package com.example.moagong.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangePasswordRequestDto {

    @NotBlank
    private final String now_password;

    @NotBlank
    private final String new_password;
}
