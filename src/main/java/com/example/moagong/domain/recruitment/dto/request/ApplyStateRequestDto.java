package com.example.moagong.domain.recruitment.dto.request;

import com.example.moagong.domain.recruitment.entity.applyState;
import com.example.moagong.global.exception.CustomException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static com.example.moagong.global.exception.GlobalErrorCode.INVALID_INPUT_VALUE;


@Getter
@Builder
public class ApplyStateRequestDto {

    @NotNull
    private final applyState state;

    public ApplyStateRequestDto(applyState state){
        if(state != applyState.ACCEPT && state != applyState.REFUSE)
            throw new CustomException(INVALID_INPUT_VALUE);
        this.state = state;
    }
}
