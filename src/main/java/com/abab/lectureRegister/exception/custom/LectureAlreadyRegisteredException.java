package com.abab.lectureRegister.exception.custom;

public class LectureAlreadyRegisteredException extends RuntimeException {
    public LectureAlreadyRegisteredException() {
        super("Already registered lecture");
    }
}
