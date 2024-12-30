package com.abab.lectureRegister.service;

import com.abab.lectureRegister.exception.custom.LectureAlreadyRegisteredException;
import com.abab.lectureRegister.model.Lecture;
import com.abab.lectureRegister.model.Registration;
import com.abab.lectureRegister.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final RegistrationRepository registrationRepository;

    public void isNotRegisteredYet(Long userId, Lecture lecture) {
        Optional<Registration> isRegistered = registrationRepository.findByUserIdAndLecture(userId, lecture);
        if (isRegistered.isPresent()) {
            throw new LectureAlreadyRegisteredException();
        }
    }

    public Registration register(Long userId, Lecture lecture) {
        Registration registration = Registration.builder()
                .userId(userId)
                .lecture(lecture)
                .build();
        return registrationRepository.save(registration);
    }

    public List<Lecture> getRegisteredLectures(Long userId) {
        return registrationRepository.findLecturesByUserId(userId);
    }
}
