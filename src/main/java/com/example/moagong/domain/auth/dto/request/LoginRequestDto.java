package com.example.moagong.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@AllArgsConstructor
public class LoginRequestDto {

    @NotBlank
    private final String user_id;

    @NotBlank
    private final String password;
}
