package com.abab.lectureRegister.exception;

import com.abab.lectureRegister.exception.custom.LectureAlreadyRegisteredException;
import com.abab.lectureRegister.exception.custom.LectureFullException;
import com.abab.lectureRegister.exception.custom.LectureNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(LectureNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLectureNotFoundException(LectureNotFoundException e) {
        log.error("Lecture not found", e);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

    @ExceptionHandler(LectureAlreadyRegisteredException.class)
    public ResponseEntity<ErrorResponse> handleLectureAlreadyRegisteredException(LectureAlreadyRegisteredException e) {
        log.error("Lecture already registered", e);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage()));
    }

    @ExceptionHandler(LectureFullException.class)
    public ResponseEntity<ErrorResponse> handleLectureFullException(LectureFullException e) {
        log.error("Lecture enrollment is full", e);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage()));
    }
}
