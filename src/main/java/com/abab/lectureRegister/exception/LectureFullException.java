package com.abab.lectureRegister.exception;

public class LectureFullException extends RuntimeException {
    public LectureFullException(Long lectureId) { super("Lecture ID " + lectureId + " is full");}
}
