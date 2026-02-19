package com.example.moagong.domain.user.dto.request;

import com.example.moagong.domain.model.Birth;
import com.example.moagong.domain.model.Phone;
import com.example.moagong.domain.model.dto.BirthRequest;
import com.example.moagong.domain.model.dto.BirthResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class UserRequestDto {

    @NotBlank
    private final String user_id;

    @NotBlank
    private final String password;

    @NotBlank
    private final String name;

    private final BirthRequest birth;

    @NotBlank
    private final String phone;

    @Email
    @NotBlank
    private final String email;
}
