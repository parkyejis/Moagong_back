package com.example.moagong.domain.contest.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class AllContestResponseDto {
    private final Long contest_id;
    private final String title;
    private final LocalDate start;
    private LocalDate end;
}
