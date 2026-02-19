package com.example.moagong.domain.recruitment.repository;

import com.example.moagong.domain.recruitment.dto.response.ApplyUserResponseDto;
import com.example.moagong.domain.recruitment.entity.RecruitState;
import com.example.moagong.domain.recruitment.entity.RecruitmentApply;
import com.example.moagong.domain.recruitment.entity.applyState;
import jakarta.validation.constraints.Positive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecruitmentApplyRepository extends JpaRepository <RecruitmentApply, Long> {

    boolean existsByRecruitmentIdAndMasterId(Long recruitment_id, String master_id);
    Optional<RecruitmentApply> findByMasterId(String master_id);

    @Query("select r from RecruitmentApply r where r.recruitment.id = :recruitmentId and r.apply_id = :applyId")
    Optional<RecruitmentApply> findByRecruitmentIdAndApplyId(@Param("recruitmentId")Long recruitment_id, @Param("applyId")Long apply_id);

    @Query("select r from RecruitmentApply r where r.recruitment.id = :recruitmentId and r.apply_user.id = :apply_userId")
    Optional<RecruitmentApply> findByRecruitmentIdAndApplyUserId(@Param("recruitmentId") Long recruitment_id, @Param("apply_userId") String apply_user);

    @Query("select new com.example.moagong.domain.recruitment.dto.response.ApplyUserResponseDto(u.id, u.name, u.level, r.introduce, r.state) from RecruitmentApply r join r.apply_user u where r.recruitment.id = :recruitment_id")
    List<ApplyUserResponseDto> findByRecruitmentId(@Param("recruitment_id")Long recruitment_id);

    @Modifying(clearAutomatically = true)
    @Query("update RecruitmentApply r set r.state = :newState where r.apply_id = :applyId and r.state = :expectedState")
    int updateStateIfMatch(@Param("applyId") Long applyId, @Param("expectedState") applyState expectedState, @Param("newState") applyState newState);

    @Query("select count(r) from RecruitmentApply r where r.recruitment.id = :recruitmentId and r.state = :expectedState")
    Long countByRecruitmentIdAndState(@Param("recruitmentId") Long recruitmentId, @Param("expectedState") applyState expectedState);


}
