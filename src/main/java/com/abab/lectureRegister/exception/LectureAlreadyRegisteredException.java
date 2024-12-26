package com.abab.lectureRegister.exception;

public class LectureAlreadyRegisteredException extends RuntimeException {
    public LectureAlreadyRegisteredException() {
        super("Already registered lecture");
    }
}
