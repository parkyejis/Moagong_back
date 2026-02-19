package com.example.moagong.domain.recruitment.repository;

import com.example.moagong.domain.contest.dto.response.AllContestResponseDto;
import com.example.moagong.domain.recruitment.dto.response.ContestRecruitmentResponseDto;
import com.example.moagong.domain.recruitment.dto.response.UserRecruitmentResponseDto;
import com.example.moagong.domain.recruitment.entity.RecruitState;
import com.example.moagong.domain.recruitment.entity.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {

    boolean existsByContestIdAndUserId(Long contest_id, String user_id);
    boolean existsByContestId(Long contest_id);
    boolean existsByUserId(String user_id);

    @Query("select new com.example.moagong.domain.recruitment.dto.response.ContestRecruitmentResponseDto(r.id, r.title, r.comment) from Recruitment r where r.contest.id = :contest_id and r.state = :state")
    List<ContestRecruitmentResponseDto> findByContestIdAndState(@Param("contest_id") Long contest_id, @Param("state") RecruitState state);

    @Query("select new com.example.moagong.domain.recruitment.dto.response.UserRecruitmentResponseDto(r.id, r.title, r.user.id, r.start, r.end) from Recruitment r where r.user.id = :user_id and r.state = :state")
    List<UserRecruitmentResponseDto> findByUserIdAndState(@Param("user_id") String user_id, @Param("state") RecruitState state);

    @Modifying(clearAutomatically = true)
    @Query("update Recruitment r set r.state = :newState where r.id = :recruitmentId and r.state = :expectedState")
    int updateState(@Param("recruitmentId")Long recruitmentId, @Param("expectedState") RecruitState expectedState, @Param("newState") RecruitState newState);}
