package com.example.moagong.domain.recruitment.service;

import com.example.moagong.domain.contest.entity.Contest;
import com.example.moagong.domain.contest.repository.ContestRepository;
import com.example.moagong.domain.recruitment.dto.request.ApplyRequestDto;
import com.example.moagong.domain.recruitment.dto.request.ApplyStateRequestDto;
import com.example.moagong.domain.recruitment.dto.request.RecruitmentRequestDto;
import com.example.moagong.domain.recruitment.dto.response.ApplyUserResponseDto;
import com.example.moagong.domain.recruitment.dto.response.ContestRecruitmentResponseDto;
import com.example.moagong.domain.recruitment.dto.response.RecruitmentResponseDto;
import com.example.moagong.domain.recruitment.dto.response.UserRecruitmentResponseDto;
import com.example.moagong.domain.recruitment.entity.RecruitState;
import com.example.moagong.domain.recruitment.entity.Recruitment;
import com.example.moagong.domain.recruitment.entity.RecruitmentApply;
import com.example.moagong.domain.recruitment.entity.applyState;
import com.example.moagong.domain.recruitment.repository.RecruitmentApplyRepository;
import com.example.moagong.domain.recruitment.repository.RecruitmentRepository;
import com.example.moagong.domain.user.entity.User;
import com.example.moagong.domain.user.repository.UserRepository;
import com.example.moagong.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.example.moagong.domain.recruitment.entity.RecruitState.*;
import static com.example.moagong.domain.recruitment.entity.applyState.*;
import static com.example.moagong.global.exception.error.ContestErrorCode.CONTEST_NOT_FOUND;
import static com.example.moagong.global.exception.error.RecruitmentErrorCode.*;
import static com.example.moagong.global.exception.error.UserErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final RecruitmentApplyRepository recruitmentApplyRepository;
    private final UserRepository userRepository;
    private final ContestRepository contestRepository;

    @Transactional
    public Long createRecruitment(String user_id, Long contest_id, RecruitmentRequestDto dto){
        //1. user, contest 유효한지 검사하기
        User user = userRepository.findById(user_id).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Contest contest = contestRepository.findById(contest_id).orElseThrow(() -> new CustomException(CONTEST_NOT_FOUND));

        //2. 유저가 해당 contest의 모집공고를 가지고 있는지 확인 -> 1차 적인 유니크 제약 조건을 달아놓은 후, 이차 적으로 해당 메서드에서 존재 검사
        if(recruitmentRepository.existsByContestIdAndUserId(contest_id, user_id))
            throw new CustomException(ALREADY_CREATED);

        //3. 모집공고 생성하기
        Recruitment recruitment = Recruitment.builder()
                .title(dto.getTitle())
                .count(dto.getCount())
                .comment(dto.getComment())
                .user(user)
                .contest(contest)
                .build();

        try {
            recruitmentRepository.save(recruitment);
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(ALREADY_CREATED);
        }

        //모집공고 참가자에 명단에 추가해 주어야함
        RecruitmentApply apply = RecruitmentApply.builder()
                .state(ACCEPT)
                .apply_user(user)
                .recruitment(recruitment)
                .build();

        recruitmentApplyRepository.save(apply);
        return recruitment.getId();
    }

    //삭제
    @Transactional
    public void deleteRecruitment(String user_id, Long recruitment_id){
        if(!recruitmentRepository.existsByUserId(user_id))
            throw new CustomException(NO_PERMMISSION);
        recruitmentRepository.deleteById(recruitment_id);
    }

    //올라온 공고 보기
    @Transactional(readOnly = true)
    public List<ContestRecruitmentResponseDto> getContestRecruitments(Long contest_id) {
        //1. contest_id 로 해당 대회 존재하는지 확인하기
        if(!recruitmentRepository.existsByContestId(contest_id)) throw new CustomException(CONTEST_NOT_FOUND);
        //2. 해당 contest에대한 모집글 필요한 정보만 dto로 받아오기
        List<ContestRecruitmentResponseDto> list = recruitmentRepository.findByContestIdAndState(contest_id, UNCONFIRME);
        return list;
    }

    //내가 올린 공고 보기
    @Transactional(readOnly = true)
    public List<UserRecruitmentResponseDto> getUserRecruitments(String user_id, RecruitState state) {
        //1. 모집글 리스트 가져오기 (모집중인 글)
        List<UserRecruitmentResponseDto> list = recruitmentRepository.findByUserIdAndState(user_id, state);
        return  list;
    }

    @Transactional(readOnly = true)
    public RecruitmentResponseDto getRecruitment(Long recruit_id) {
        //있는지 확인
        Recruitment recruit = recruitmentRepository.findById(recruit_id).orElseThrow(() -> new CustomException(NOT_FOUND_RECRUIT));

        return RecruitmentResponseDto.builder()
                .title(recruit.getTitle())
                .master_id(recruit.getUser().getId())
                .comment(recruit.getComment())
                .build();
    }

    @Transactional(readOnly = true)
    public List<ApplyUserResponseDto> getApplyUserList(Long recruit_id) {
        //해당 모집 공고가 존재하는지 확인
        if(!recruitmentRepository.existsById(recruit_id)) throw new CustomException(NOT_FOUND_RECRUIT);
        //모집공고의 참가자 list 가져오기
        List<ApplyUserResponseDto> list = recruitmentApplyRepository.findByRecruitmentId(recruit_id);

        return list;
    }

    //참가 신청하기
    @Transactional
    public void applyMember(ApplyRequestDto dto, Long recruitment_id, String user_id) {

        Recruitment recruitment = recruitmentRepository.findById(recruitment_id).orElseThrow(() -> new CustomException(NOT_FOUND_RECRUIT));
        User user = userRepository.findById(user_id).orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        //한곳에 지원 한 번만 할 수 있음
        if(recruitmentApplyRepository.existsByRecruitmentIdAndMasterId(recruitment_id, user_id)) throw new CustomException(ALREADY_APPLY);

        RecruitmentApply apply = RecruitmentApply.builder()
                .introduce(dto.getIntroduce())
                .apply_user(user)
                .recruitment(recruitment)
                .master(recruitment.getUser())
                .build();

        recruitmentApplyRepository.save(apply);
    }

//    //참가 신청 수락하기ㄱ
//    @Transactional
//    public void acceptMember(String master_id, String apply_user, Long recruitment_id) {
//        decideApply(master_id, apply_user, recruitment_id, ACCEPT);
//    }
//
//    //참가 거절하기
//    @Transactional
//    public void refuseMember(String master_id, String apply_user, Long recruitment_id){
//        decideApply(master_id, apply_user, recruitment_id, REFUSE);
//    }
//
//    // 상태 값 바꿔주기
//    private void decideApply(String master_id, String apply_user, Long recruitment_id, applyState newState){
//        //1. 권한 검증 -> 방장이 맞는지
//        RecruitmentApply apply = recruitmentApplyRepository.findByRecruitmentIdAndApplyUserId(recruitment_id, apply_user).orElseThrow(() -> new CustomException(NO_PERMMISSION));
//        //2. 대상 검증 -> 신청자가 맞는지, 존재하는지
//        if(!apply.getMaster().getId().equals(master_id)) throw new CustomException(NO_PERMMISSION);
//        //수락하는 경우 인원수를 넘으면 수락 불가능
//        if(newState.equals(ACCEPT) &&
//                recruitmentApplyRepository.countByRecruitmentIdAndState(recruitment_id, ACCEPT)==apply.getRecruitment().getCount()) throw new CustomException(ALREADY_MEMBER);
//
//        //3. 상태 전이 검증
//        if(recruitmentApplyRepository.updateStateIfMatch(apply.getApply_id(), apply.getState(), newState)==0){
//            throw new CustomException(APPLY_ALREADY_PROCESSED);
//        }
//    }

    /*유저 아이디가 아닌 apply_id 값으로 해결하는 것이 좋음
    * 악의적으로 다른 유저의 id를 전달하여 상태 변환하는 것을 방지 */
    @Transactional
    public void decideApply(ApplyStateRequestDto dto, String user_id, Long apply_id, Long recruitment_id) {
        //1. 대상 검증 -> 신청자가 맞는지, 존재하는 모집공고 인지
        RecruitmentApply recruitmentApply = recruitmentApplyRepository.findByRecruitmentIdAndApplyId(recruitment_id, apply_id)
                .orElseThrow(() -> new CustomException(NOT_FOUND_RECRUIT));

        //2. 권한 검사 -> 방장이 맞는지
        if(!recruitmentApply.getMaster().getId().equals(user_id))
            throw new CustomException(NO_PERMMISSION);
        //3. 수락하는 경우 인원 수 넘으면 수락 불가능
        if(dto.getState().equals(ACCEPT) &&
                recruitmentApplyRepository.countByRecruitmentIdAndState(recruitment_id, ACCEPT)
                        ==recruitmentApply.getRecruitment().getCount()) {
            throw new CustomException(ALREADY_MEMBER);
        }
        //4. 상태 전이 검증
        if(recruitmentApplyRepository.updateStateIfMatch(recruitmentApply.getApply_id(), recruitmentApply.getState(), dto.getState()) == 0){
            throw new CustomException(APPLY_ALREADY_PROCESSED);
        }
    }


    //팀 확정하기
    @Transactional
    public void confirmTeam(String user_id, Long recruitment_id) {
        changeRecruitState(user_id, recruitment_id, CONFIRME);
    }

    //팀 끝내기
    @Transactional
    public void closeTeam(String user_id, Long recruitment_id){
        changeRecruitState(user_id, recruitment_id, CLOSE);
    }

    //팀 상태 바꿔주기
    private void changeRecruitState(String user_id, Long recruitment_id, RecruitState newState){
        //1. 공모전 존재 확인
        Recruitment recruitment = recruitmentRepository.findById(recruitment_id).orElseThrow(() -> new CustomException(NOT_FOUND_RECRUIT));
        //2. 방장의 권한 확인
        if(!recruitment.getUser().getId().equals(user_id)) throw new CustomException(NO_PERMMISSION);
        //3. 모집 공고 팀 확정 조건 확인하기
        if(newState.equals(CONFIRME)){
            // -> 참가자 인원수 맞게 되었는지
            Long count = recruitmentApplyRepository.countByRecruitmentIdAndState(recruitment_id, ACCEPT);
            if(recruitment.getCount() != recruitmentApplyRepository.countByRecruitmentIdAndState(recruitment_id, ACCEPT))
                throw new CustomException(NOT_ALREADY_MEMBER);
            // -> 공모전 기간 안에 되어있는지 (CONFIRM으로 변경 시)
            if(LocalDate.now().isAfter(recruitment.getEnd()))
                throw new CustomException(EXPIRED_RECRUITMENT);
            recruitmentRepository.updateState(recruitment_id, recruitment.getState(), newState);
        }
        else if(newState.equals(CLOSE)){
            // -> 기간 끝난 후 close하는 경우 -> 추후 끝낸 공모전으로 정보 이동

            recruitmentRepository.updateState(recruitment_id, recruitment.getState(), newState);
        }
        // ->
    }

}
