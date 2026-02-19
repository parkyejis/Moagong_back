package com.example.moagong.domain.model;

import com.example.moagong.global.exception.CustomException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.example.moagong.global.exception.GlobalErrorCode.INVALID_INPUT_VALUE;
import static com.example.moagong.global.exception.GlobalErrorCode.RESOURCE_NOT_FOUND;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //아무나 new Email()을 통해 객체를 꺼내와 규칙 없이 Email을 만드는 위험을 없애기 위해
@ToString(of={"value"})
public class Email {

    @Column(nullable = false)
    private String value;

    private Email (String value){
        this.value = value;
    }

    public static Email of(String value) {
        if(value == null) throw new CustomException(RESOURCE_NOT_FOUND);
        if(!value.contains("@")) throw new CustomException(INVALID_INPUT_VALUE);

        return new Email(value);
    }
}
