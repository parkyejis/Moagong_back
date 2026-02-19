package com.example.moagong.domain.user.dto.request;

import com.example.moagong.domain.model.dto.BirthRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class ChangeInfoRequestDto {

    @NotBlank
    private final String name;

    private final BirthRequest birth;

    @NotBlank
    private final String phone;

    @NotBlank
    @Email
    private final String email;
}
