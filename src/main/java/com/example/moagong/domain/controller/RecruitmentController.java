package com.example.moagong.domain.controller;

import com.example.moagong.domain.recruitment.dto.request.RecruitmentRequestDto;
import com.example.moagong.domain.recruitment.dto.response.ApplyUserResponseDto;
import com.example.moagong.domain.recruitment.dto.response.ContestRecruitmentResponseDto;
import com.example.moagong.domain.recruitment.dto.response.RecruitmentResponseDto;
import com.example.moagong.domain.recruitment.dto.response.UserRecruitmentResponseDto;
import com.example.moagong.domain.recruitment.entity.RecruitState;
import com.example.moagong.domain.recruitment.service.RecruitmentService;
import com.example.moagong.global.response.BaseResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/recruitments")
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    //생성하기 -> 만드는 사람도 참가자이기 때문에 참가자 명단에 추가해주어야함
    @PostMapping("/create/{contest-id}")
    public ResponseEntity<BaseResponse<Long>> createRecruitment (@PathVariable(value = "contest-id")Long contest_id, @AuthenticationPrincipal String user_id, @RequestBody @Valid RecruitmentRequestDto dto){
        Long apply_id = recruitmentService.createRecruitment(user_id, contest_id, dto);
        return ResponseEntity.status(200).body(BaseResponse.success("200", "모집 공고가 생성되었습니다.", apply_id));
    }

    //삭제하기
    @DeleteMapping("/delete/{recruitment-id}")
    public ResponseEntity<BaseResponse<?>> deleteRecruitment(@AuthenticationPrincipal String user_id, @PathVariable (value = "recruitment-id") Long recruitment_id) {
        recruitmentService.deleteRecruitment(user_id, recruitment_id);
        return ResponseEntity.status(200).body(BaseResponse.success(null));
    }

    //contest에 따른 공고 정보 list 가져오기 (올라온 공고 보기)
    @GetMapping("/getlist/{contest-id}")
    public ResponseEntity<BaseResponse<List<ContestRecruitmentResponseDto>>> getContestRecruitments(@PathVariable(value="contest-id") @Positive Long contest_id){
        List<ContestRecruitmentResponseDto> list = recruitmentService.getContestRecruitments(contest_id);
        return ResponseEntity.status(200).body(BaseResponse.success("200", "모든 공고 출력", list));
    }

    //user에 따른 공고 정보 list 가져오기 (내가 올린 공고 보기_공고 진행중)
    @GetMapping("/getlist/user")
    public ResponseEntity<BaseResponse<List<UserRecruitmentResponseDto>>> getUserRecruitments(@AuthenticationPrincipal String user_id) {
        List<UserRecruitmentResponseDto> list = recruitmentService.getUserRecruitments(user_id, RecruitState.UNCONFIRME);
        return ResponseEntity.status(200).body(BaseResponse.success("200", "모집중인 모집공고 리스트", list));
    }

    //공고가 끝난 후 공모전 진행중인 공고
    @GetMapping("/startlist/user")
    public ResponseEntity<BaseResponse<List<UserRecruitmentResponseDto>>> getStartUserRecruitments(@AuthenticationPrincipal String user_id) {
        List<UserRecruitmentResponseDto> list = recruitmentService.getUserRecruitments(user_id, RecruitState.CONFIRME);
        return ResponseEntity.status(200).body(BaseResponse.success("200", "모집중인 모집공고 리스트", list));
    }


//    @GetMapping("/recruitlist")
//    public ResponseEntity<BaseResponse<List<UserRecruitmentResponseDto>>> getUserRecruitments (){
//
//    }

    //공고 디테일한 정보 가져오기
    @GetMapping("/detail/{recruit-id}")
    public ResponseEntity<BaseResponse<RecruitmentResponseDto>> getRecruitment(@PathVariable(value="recruit-id") Long recruit_id){
        RecruitmentResponseDto dto = recruitmentService.getRecruitment(recruit_id);
        return ResponseEntity.status(200).body(BaseResponse.success("200", "모집공고 공고 정보", dto));
    }

    //모집공고 신청자 명단 가져오는 api
    @GetMapping("/apply_userList/{recruit-id}")
    public ResponseEntity<BaseResponse<List<ApplyUserResponseDto>>> getApplyUserList(@PathVariable(value = "recruit-id") Long recruit_id) {
        List<ApplyUserResponseDto> list = recruitmentService.getApplyUserList(recruit_id);
        return ResponseEntity.status(200).body(BaseResponse.success("200", "참가자 명단", list));
    }
}
