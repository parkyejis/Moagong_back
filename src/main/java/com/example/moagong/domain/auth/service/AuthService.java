package com.example.moagong.domain.auth.service;

import com.example.moagong.domain.auth.dto.request.LoginRequestDto;
import com.example.moagong.domain.auth.dto.response.LoginResponseDto;
import com.example.moagong.domain.auth.token.JwtProvider;
import com.example.moagong.domain.user.entity.User;
import com.example.moagong.domain.user.repository.UserRepository;
import com.example.moagong.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.moagong.global.exception.error.UserErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto dto){
        User user = userRepository.findById(dto.getUser_id()).orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword()))
            throw new CustomException(USER_NOT_FOUND);

        String token = jwtProvider.createAccessToken(user.getId(), "USER");

        return new LoginResponseDto(token);
    }
}
