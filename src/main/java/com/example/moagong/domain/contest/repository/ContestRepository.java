package com.example.moagong.domain.contest.repository;

import com.example.moagong.domain.contest.dto.response.AllContestResponseDto;
import com.example.moagong.domain.contest.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContestRepository extends JpaRepository<Contest, Long> {
    @Query("select new com.example.moagong.domain.contest.dto.response.AllContestResponseDto(c.id, c.title, c.start, c.end) from Contest c")
    List<AllContestResponseDto> findByAllList();

}
