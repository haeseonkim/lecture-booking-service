package com.abab.lectureRegister.lecture;

import com.abab.lectureRegister.registration.Registration;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class LectureResponse {
    private Long lectureId;
    private String lectureName;
    private int currentEnrollment;
    private LocalDateTime startDateTime;

    @Builder
    public LectureResponse(Lecture lecture) {
        this.lectureId = lecture.getLectureId();
        this.lectureName = lecture.getLectureName();
        this.currentEnrollment = lecture.getCurrentEnrollment();
        this.startDateTime = lecture.getStartDateTime();
    }
}