package com.example.moagong.domain.contest.service;

import com.example.moagong.domain.contest.dto.request.ContestRequestDto;
import com.example.moagong.domain.contest.dto.response.AllContestResponseDto;
import com.example.moagong.domain.contest.dto.response.ContestResponseDto;
import com.example.moagong.domain.contest.entity.Contest;
import com.example.moagong.domain.contest.repository.ContestRepository;
import com.example.moagong.global.exception.CustomException;
import com.example.moagong.global.exception.model.BaseErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static com.example.moagong.global.exception.GlobalErrorCode.RESOURCE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ContestService {

    private final ContestRepository contestRepository;

    @Transactional
    public void createContest(ContestRequestDto dto){
        Contest contest = Contest.builder()
                .title(dto.getTitle())
                .start(dto.getStart())
                .end(dto.getEnd())
                .study(dto.getStudy())
                .host(dto.getHost())
                .explanation(dto.getExplanation())
                .build();

        contestRepository.save(contest);
    }

    @Transactional(readOnly = true)
    public List<AllContestResponseDto> getAllContest(){
        //토큰값으로 확인하도록 변경하기
        List<AllContestResponseDto> list = new LinkedList<>();
        list = contestRepository.findByAllList();
        return list;
    }

    @Transactional(readOnly = true)
    public ContestResponseDto getContest(Long contest_id){
        Contest contest = contestRepository.findById(contest_id).orElseThrow(() -> new CustomException(RESOURCE_NOT_FOUND));

        return ContestResponseDto.builder()
                .title(contest.getTitle())
                .start(contest.getStart())
                .end(contest.getEnd())
                .host(contest.getHost())
                .study(contest.getStudy())
                .explanation((contest.getExplanation()))
                .build();
    }


}
