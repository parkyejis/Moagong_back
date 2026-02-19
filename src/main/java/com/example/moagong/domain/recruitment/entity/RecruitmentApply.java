package com.example.moagong.domain.recruitment.entity;

import com.example.moagong.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(
        name = "recruitmentApply",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_recruitment_user",
                        columnNames = {"recruitment_id", "apply_user_id"}
                )
        }
)
public class RecruitmentApply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_id")
    private Long apply_id;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private applyState state = applyState.APPLY;

    @Column(name = "introduce")
    private String introduce;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_user", nullable = false)
    private User apply_user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment", nullable = false)
    private Recruitment recruitment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master", nullable = false)
    private User master;

}
