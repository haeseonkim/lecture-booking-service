package com.abab.lectureRegister.registration;

import com.abab.lectureRegister.lecture.Lecture;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDateTime registrationTime;
}
