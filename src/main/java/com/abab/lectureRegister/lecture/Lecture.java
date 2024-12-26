package com.abab.lectureRegister.lecture;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureId;

    @Column(nullable = false)
    private String lectureName;

    @Column(nullable = false)
    private int currentEnrollment = 0;

    @Column(nullable = false)
    private LocalDateTime startTime;
}
