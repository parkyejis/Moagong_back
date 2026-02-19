package com.example.moagong.domain.recruitment.entity;
import com.example.moagong.domain.contest.entity.Contest;
import com.example.moagong.domain.user.entity.User;
import com.example.moagong.global.exception.CustomException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.example.moagong.global.exception.error.RecruitmentErrorCode.NOT_FOUND_RECRUIT;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "recruitment",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_recruitment_contest_user",
                        columnNames = {"contest_id", "user_id"}
                )
        })
public class Recruitment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="recruitment_id")
    private Long id;

    //한 사람당 공고 하나만 작성 가능
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id", nullable = false)
    private Contest contest;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="comment")
    private String comment;

    @Column(name="count", nullable = false)
    private int count;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name ="state", nullable = false)
    private RecruitState state = RecruitState.UNCONFIRME;

    @Column(name="start", nullable = false)
    private LocalDate start;

    @Column(name="end", nullable = false)
    private LocalDate end;

    /*
    * contest가 영속 상태이어야 한다
    * contest가 저장되어있어야 함 */
    @PrePersist //recruitRepository.save() 하면 시점에서 해당 어노테이션을 실행하고 DB에 insert해주는 순서이다
    @PreUpdate  //contest의 기간이 바뀌면 여기서도 자동으로 변경
    private void syncContestPeriod() {
        if(this.contest == null){
            throw new CustomException(NOT_FOUND_RECRUIT);
        }
        this.start = this.contest.getStart();
        this.end = this.contest.getEnd();

    }

    //참가 신청자
    //private List<User> applicant;

    //참가자
    //private List<User> participant;

}
