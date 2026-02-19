package com.example.moagong.domain.user.dto.response;

import com.example.moagong.domain.model.Birth;
import com.example.moagong.domain.model.Email;
import com.example.moagong.domain.model.Phone;
import com.example.moagong.domain.model.dto.BirthResponse;
import com.example.moagong.domain.user.entity.Level;
import lombok.Builder;
import lombok.Getter;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@Builder
@Getter
public class InformationResponseDto {
    private final String user_id;
    private final String name;
    private final BirthResponse birth;
    private final String phone;
    private final String email;
    private final Level level;
}
