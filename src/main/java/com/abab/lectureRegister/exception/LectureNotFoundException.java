package com.abab.lectureRegister.exception;

public class LectureNotFoundException extends RuntimeException {
    public LectureNotFoundException(Long lectureId) {
        super("Lecture ID " + lectureId + " is Not Found");
    }
}
