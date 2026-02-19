package com.example.moagong.domain.recruitment.dto.response;

import com.example.moagong.domain.recruitment.entity.applyState;
import com.example.moagong.domain.user.entity.Level;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApplyUserResponseDto {

    private final String user_id;
    private final String name;
    private final Level level;
    private final String introduce;
    private final applyState state;
}
