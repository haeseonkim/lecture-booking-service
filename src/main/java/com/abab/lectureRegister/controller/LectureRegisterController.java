package com.abab.lectureRegister.controller;

import com.abab.lectureRegister.dto.RegisterLectureRequest;
import com.abab.lectureRegister.service.LectureRegisterServiceFacade;
import com.abab.lectureRegister.dto.LectureResponse;
import com.abab.lectureRegister.dto.RegistrationResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/lectures")
public class LectureRegisterController {
    private final LectureRegisterServiceFacade lectureRegisterServiceFacade;

    @GetMapping
    public ResponseEntity<List<LectureResponse>> getDateLecture(
            @RequestParam(required = false) LocalDateTime startDateTime
    ) {
        return ResponseEntity.ok(lectureRegisterServiceFacade.getLectures(startDateTime));
    }

    @PostMapping("/{lectureId}/registrations")
    public ResponseEntity<RegistrationResponse> registerLecture(
            @PathVariable Long lectureId,
            @RequestParam RegisterLectureRequest registerLectureRequest
    ) {
        return ResponseEntity.ok(lectureRegisterServiceFacade.registerLecture(registerLectureRequest.getUserId(), lectureId));
    }

    @GetMapping("/users/{userId}/registrations")
    public ResponseEntity<List<LectureResponse>> getRegisteredLectures(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(lectureRegisterServiceFacade.getRegisteredLectures(userId));
    }

}
