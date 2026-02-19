package com.example.moagong.domain.auth.controller;

import com.example.moagong.domain.auth.dto.request.LoginRequestDto;
import com.example.moagong.domain.auth.dto.response.LoginResponseDto;
import com.example.moagong.domain.auth.service.AuthService;
import com.example.moagong.domain.auth.token.JwtProvider;
import com.example.moagong.domain.user.repository.UserRepository;
import com.example.moagong.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResponseDto>> login(@RequestBody @Valid LoginRequestDto dto){
        LoginResponseDto loginResponseDto = authService.login(dto);
        return ResponseEntity.status(200).body(BaseResponse.success("200", "로그인 완료되었습니다", loginResponseDto));
    }

    //로그아웃

}
