package com.abab.lectureRegister.registration;

import com.abab.lectureRegister.lecture.Lecture;
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
        if (isRegistered.isEmpty()) {
            throw new IllegalStateException("Already registered for this lecture.");
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
