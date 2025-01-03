package com.abab.lectureRegister.service;

import com.abab.lectureRegister.dto.LectureResponse;
import com.abab.lectureRegister.dto.RegistrationResponse;
import com.abab.lectureRegister.model.Lecture;
import com.abab.lectureRegister.model.Registration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureRegisterServiceFacade {
    private final LectureService lectureService;
    private final RegistrationService registrationService;

    @Transactional(readOnly = true)
    public List<LectureResponse> getLectures(LocalDateTime startDateTime){
        return lectureService.getAllLectures(startDateTime).stream()
                .map(LectureResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public RegistrationResponse registerLecture(Long userId, Long lectureId) {
        Lecture lecture = getLectureWithValidation(lectureId);

        checkRegistrationEligibility(userId, lecture);

        Registration registration = registrationService.register(userId, lecture);
        lectureService.updateCurrentEnrollment(lecture);

        return new RegistrationResponse(registration);
    }

    @Transactional(readOnly = true)
    public List<LectureResponse> getRegisteredLectures(Long userId) {
        return registrationService.getRegisteredLectures(userId).stream()
                .map(LectureResponse::new)
                .collect(Collectors.toList());
    }

    private Lecture getLectureWithValidation(Long lectureId) {
        Lecture lecture = lectureService.getLectureByIdWithLock(lectureId);
        lectureService.isPossibleToRegister(lecture);
        return lecture;
    }

    private void checkRegistrationEligibility(Long userId, Lecture lecture) {
        registrationService.isNotRegisteredYet(userId, lecture);
    }
}
