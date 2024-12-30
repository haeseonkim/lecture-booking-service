package com.abab.lectureRegister.service;

import com.abab.lectureRegister.model.Lecture;
import com.abab.lectureRegister.model.Registration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureRegisterServiceFacade {
    private final LectureService lectureService;
    private final RegistrationService registrationService;

    @Transactional(readOnly = true)
    public List<Lecture> getLectures(LocalDateTime startDateTime){
        return lectureService.getAllLectures(startDateTime);
    }

    @Transactional
    public Registration registerLecture(Long userId, Long lectureId) {
        Lecture lecture = lectureService.getLectureByIdWithLock(lectureId);

        lectureService.isPossibleToRegister(lecture);
        registrationService.isNotRegisteredYet(userId, lecture);

        Registration registration = registrationService.register(userId, lecture);
        lectureService.updateCurrentEnrollment(lecture);

        return registration;
    }

    @Transactional(readOnly = true)
    public List<Lecture> getRegisteredLectures(Long userId) {
        return registrationService.getRegisteredLectures(userId);
    }
}
