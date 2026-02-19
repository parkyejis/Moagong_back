package com.example.moagong.domain.controller;

import com.example.moagong.domain.contest.dto.request.ContestRequestDto;
import com.example.moagong.domain.contest.dto.response.AllContestResponseDto;
import com.example.moagong.domain.contest.dto.response.ContestResponseDto;
import com.example.moagong.domain.contest.service.ContestService;
import com.example.moagong.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contests")
public class ContestController {

    private final ContestService contestService;

    @PostMapping("/create")
    public void createContest(@RequestBody @Valid ContestRequestDto contestRequestDto) {
        contestService.createContest(contestRequestDto);
    }


    @GetMapping("/")
    //기간이 지나지 않은 경우에만 가져오도록 수정
    public ResponseEntity<BaseResponse<List<AllContestResponseDto>>> getAllContest() {
        List<AllContestResponseDto> contestList = new LinkedList<>();
        contestList = contestService.getAllContest();

        return ResponseEntity.status(200).body(BaseResponse.success("200","모든 대회 정보 가져오기 성공", contestList));
    }

    @GetMapping("/{contest_id}")
    public ResponseEntity<BaseResponse<ContestResponseDto>> getContest(@PathVariable(value = "contest_id") Long contest_id){
        ContestResponseDto contestResponseDto = contestService.getContest(contest_id);

        return ResponseEntity.status(200).body(BaseResponse.success("200", "정보 가져오기",contestResponseDto));
    }

    /*크롤링 -> 크롤링 주기 설정 ...*/



}
