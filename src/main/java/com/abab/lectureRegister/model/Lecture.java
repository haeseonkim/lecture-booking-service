package com.abab.lectureRegister.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureId;

    @Column(nullable = false)
    private String lectureName;

    @Column(nullable = false)
    private String lecturer;

    @Column(nullable = false)
    private int currentEnrollment = 0;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    public void upCurrentEnrollment() {
        currentEnrollment++;
    }
}
