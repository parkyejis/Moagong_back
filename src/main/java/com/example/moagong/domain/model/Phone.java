package com.example.moagong.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"first", "middle", "last"})
public class Phone {
    @NotBlank
    @Column(name = "first", length = 3)
    private String first;

    @NotBlank
    @Column(name = "middle", length = 4)
    private String middle;

    @NotBlank
    @Column(name = "last", length = 4)
    private String last;

    private Phone(final String first, final String middle, final String last){
        this.first = first;
        this.middle = middle;
        this.last = last;
    }



}
