package com.example.moagong.domain.recruitment.dto.response;

import com.example.moagong.domain.recruitment.entity.RecruitState;
import com.example.moagong.domain.user.entity.Level;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class UserRecruitmentResponseDto {
//공모전 이름, 기간, 방장 이름
    private final Long recruit_id;
    private final String title;
    private final String master_id;
    private final LocalDate start;
    private final LocalDate end;
}
