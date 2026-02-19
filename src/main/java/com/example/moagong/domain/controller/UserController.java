package com.example.moagong.domain.controller;

import com.example.moagong.domain.user.dto.request.ChangeInfoRequestDto;
import com.example.moagong.domain.user.dto.request.ChangePasswordRequestDto;
import com.example.moagong.domain.user.dto.request.UserRequestDto;
import com.example.moagong.domain.user.dto.response.InformationResponseDto;
import com.example.moagong.domain.user.service.UserService;
import com.example.moagong.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<BaseResponse<Void>> createUser(@RequestBody @Valid UserRequestDto userRequestDto){
        //정보 보내고 결과 받아오기
        userService.createUser(userRequestDto);
        return ResponseEntity.status(201).body(BaseResponse.success("201", "회원가입이 완료되었습니다.", null));
    }

//    @PostMapping("/login")
//    public ResponseEntity<BaseResponse<Void>> login(@RequestBody @Valid LoginRequestDto dto) {
//        userService.login(dto);
//        return ResponseEntity.status(200).body(BaseResponse.success("200", "로그인되었습니다",null));
//    }

    @GetMapping("/me")
    public ResponseEntity<BaseResponse<InformationResponseDto>> getInformation(@AuthenticationPrincipal String user_id) {
        InformationResponseDto informationResponseDto = userService.getInfo(user_id);
        return ResponseEntity.status(200).body(BaseResponse.success("200", "정보보기", informationResponseDto));
    }

    @PutMapping("/changeInfo")
    public ResponseEntity<BaseResponse<InformationResponseDto>> changeInformation(@AuthenticationPrincipal String user_id, @RequestBody @Valid ChangeInfoRequestDto dto) {
        InformationResponseDto informationResponseDto = userService.changeInfo(user_id, dto);
        return ResponseEntity.status(201).body(BaseResponse.success("200", "변경된 정보", informationResponseDto));
    }

    @PatchMapping("/changePassword")
    public ResponseEntity<BaseResponse<Void>> changePassword(@AuthenticationPrincipal String user_id, @RequestBody @Valid ChangePasswordRequestDto dto) {
        userService.changePassword(user_id, dto);
        return ResponseEntity.status(201).body(BaseResponse.success("200", "변경된 정보", null));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BaseResponse<Void>> deleteUser(@AuthenticationPrincipal String user_id) {
        userService.deleteUser(user_id);
        return ResponseEntity.status(201).body(BaseResponse.success("200", "회원 탈퇴", null));
    }

}
