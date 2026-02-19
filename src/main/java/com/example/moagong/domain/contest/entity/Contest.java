package com.example.moagong.domain.contest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contest")
public class Contest {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "contest_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "start")
    private LocalDate start;

    @Column(name = "end")
    private LocalDate end;

    @Column(name = "host")
    private String host;      //주최기관

    @Column(name = "study")
    private String study;      //전공/분야

    @Column(name = "explanation")
    private String explanation; //설명
}
