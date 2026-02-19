package com.example.moagong.domain.user.entity;

import com.example.moagong.domain.model.Birth;
import com.example.moagong.domain.model.Email;
import com.example.moagong.domain.model.Phone;
import com.example.moagong.domain.user.dto.request.ChangeInfoRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor //모든 필드를 매개변수로 받는 전체 생성자 자동으로 생성
@NoArgsConstructor  //매개변수 없는 기본 생성자 자동 생성
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id", updatable = false)
    private String id;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "year", column = @Column(name = "year", nullable = false)),
            @AttributeOverride( name = "month", column = @Column(name = "month", nullable = false)),
            @AttributeOverride( name = "day", column = @Column(name = "day", nullable = false))
    })
    private Birth birth;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Embedded
    @AttributeOverride(name = "email", column = @Column(name = "user_email", nullable = false))
    private Email email;

//    @Embedded
//    @AttributeOverrides({
//            @AttributeOverride( name = "year", column = @Column(name = "year", nullable = false)),
//            @AttributeOverride( name = "month", column = @Column(name = "month", nullable = false)),
//            @AttributeOverride( name = "day", column = @Column(name = "day", nullable = false))
//    })
//    private Birth birth;
//
//    @Embedded
//    @AttributeOverrides({
//            @AttributeOverride( name = "first", column = @Column(name = "first", nullable = false)),
//            @AttributeOverride( name = "middle", column = @Column(name = "middle", nullable = false)),
//            @AttributeOverride( name = "last", column = @Column(name = "last", nullable = false))
//    })
//    private Phone phone;
//
//    @Embedded
//    @AttributeOverride( name = "day", column = @Column(name = "day", nullable = false))
//    private Email email;

    @Builder.Default //builder()로 객체를 만들 때, 값을 안 주면 이 값으로 자동 세팅해라 라는 말 ~
    @Enumerated(EnumType.STRING)    //enum을 DB에 저장할 시 String 으로 저장한다는 말
    @Column(nullable = false)
    private Level level = Level.Gress;

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void changeInfo(String name, Birth birth, String phone, Email email){
        this.name = name;
        this.birth = birth;
        this.phone = phone;
        this.email = email;
    }

    public void changePassword(String password){
        this.password = password;
    }
}
