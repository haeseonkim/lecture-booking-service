package com.abab.lectureRegister.lecture;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class LectureResponse {
    private Long lectureId;
    private String lectureName;
    private String lecturer;
    private int currentEnrollment;
    private LocalDateTime startDateTime;

    @Builder
    public LectureResponse(Lecture lecture) {
        this.lectureId = lecture.getLectureId();
        this.lectureName = lecture.getLectureName();
        this.lecturer = lecture.getLecturer();
        this.currentEnrollment = lecture.getCurrentEnrollment();
        this.startDateTime = lecture.getStartDateTime();
    }
}