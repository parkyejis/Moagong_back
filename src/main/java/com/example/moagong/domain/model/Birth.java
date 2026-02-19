package com.example.moagong.domain.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Birth {

    @NotBlank
    private Long year;
    @NotBlank
    private Long month;
    @NotBlank
    private Long day;

    private Birth(Long year, Long month, Long day){
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public static Birth of(Long year, Long month, Long day){
        return new Birth(year, month, day);
    }
}
