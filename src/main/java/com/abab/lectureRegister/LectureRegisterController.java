package com.abab.lectureRegister;

import com.abab.lectureRegister.lecture.Lecture;
import com.abab.lectureRegister.lecture.LectureResponse;
import com.abab.lectureRegister.registration.Registration;
import com.abab.lectureRegister.registration.RegistrationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/lecture")
public class LectureRegisterController {
    private final LectureRegisterServiceFacade lectureRegisterServiceFacade;

    @GetMapping("/date")
    public ResponseEntity<List<LectureResponse>> getDateLecture(
            @RequestParam(required = false) LocalDateTime startDateTime
    ) {
        List<Lecture> lectures = lectureRegisterServiceFacade.getLectures(startDateTime);
        List<LectureResponse> response = lectures.stream()
                .map(LectureResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{lectureId}/register")
    public ResponseEntity<RegistrationResponse> registerLecture(
            @PathVariable Long lectureId,
            @RequestParam Long userId
    ) {
        Registration registration = lectureRegisterServiceFacade.registerLecture(userId, lectureId);
        return ResponseEntity.ok(new RegistrationResponse(registration));
    }

    @GetMapping("/registered")
    public ResponseEntity<List<LectureResponse>> getRegisteredLectures(
            @RequestParam Long userId
    ) {
        List<Lecture> lectures = lectureRegisterServiceFacade.getRegisteredLectures(userId);
        List<LectureResponse> response = lectures.stream()
                .map(LectureResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

}
