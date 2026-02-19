package com.example.moagong.domain.controller;

import com.example.moagong.domain.recruitment.dto.request.ApplyRequestDto;
import com.example.moagong.domain.recruitment.service.RecruitmentService;
import com.example.moagong.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/apply")
public class RecruitApplyController {

    private final RecruitmentService recruitmentService;

    //지원 신청하기
    @PostMapping("/{recruitment-id}")
    public ResponseEntity<BaseResponse<Void>> applyMember(@RequestBody ApplyRequestDto dto, @PathVariable(value = "recruitment-id") Long recruitment_id, @AuthenticationPrincipal String user_id){
        recruitmentService.applyMember(dto, recruitment_id, user_id);
        return ResponseEntity.status(200).body(BaseResponse.success(null));
    }

    //지원 신청 수락하기
    @PatchMapping("/{recruitment-id}/change/{apply-user}/accept")
    public ResponseEntity<BaseResponse<Void>> acceptMember(@AuthenticationPrincipal String user_id ,@PathVariable(value="recruitment-id")Long recruitment_id, @PathVariable(value = "apply-user")String apply_user) {
        recruitmentService.acceptMember(user_id, apply_user, recruitment_id);
        return ResponseEntity.status(200).body(BaseResponse.success(null));
    }

    //지원 신청 거절하기
    @PatchMapping("/{recruitment-id}/change/{apply-user}/refuse")
    public ResponseEntity<BaseResponse<Void>> refuseMember(@AuthenticationPrincipal String user_id ,@PathVariable(value="recruitment-id")Long recruitment_id, @PathVariable(value = "apply-user")String apply_user) {
        recruitmentService.refuseMember(user_id, apply_user, recruitment_id);
        return ResponseEntity.status(200).body(BaseResponse.success(null));
    }

    //팀 확정하기
    @PatchMapping("/{recruitment-id}/makeTeam")
    public ResponseEntity<BaseResponse<Void>> confirmTeam(@AuthenticationPrincipal String user_id, @PathVariable(value = "recruitment-id") Long recruitment_id) {
        recruitmentService.confirmTeam(user_id, recruitment_id);
        return ResponseEntity.status(200).body(BaseResponse.success(null));
    }

    //삭제하기
    @PatchMapping("/{recruitment-id}/closeTeam")
    public ResponseEntity<BaseResponse<Void>> closeTeam(@AuthenticationPrincipal String user_id, @PathVariable(value = "recruitment-id") Long recruitment_id){
        recruitmentService.closeTeam(user_id, recruitment_id);
        return ResponseEntity.status(200).body(BaseResponse.success(null));
    }

}
