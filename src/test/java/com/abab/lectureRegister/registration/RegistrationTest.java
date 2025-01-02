package com.abab.lectureRegister.registration;

import com.abab.lectureRegister.exception.LectureAlreadyRegisteredException;
import com.abab.lectureRegister.lecture.Lecture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegistrationTest {
    @Mock
    private RegistrationRepository registrationRepository;

    @InjectMocks
    private RegistrationService registrationService;

    @Test
    void 이미_신청한_특강이면_LectureAlreadyRegisteredException(){
        Long userId = 1L;
        LocalDateTime targetDate = LocalDateTime.of(2024, 12, 1, 10, 0);
        Lecture lecture = Lecture.builder()
                .lectureId(1L)
                .lectureName("Java 핵심 파헤치기")
                .startDateTime(targetDate)
                .currentEnrollment(20)
                .build();
        Registration registration = Registration.builder()
                .userId(userId)
                .lecture(lecture)
                .build();

        when(registrationRepository.findByUserIdAndLecture(userId, lecture)).thenReturn(Optional.ofNullable(registration));

        // when & then
        assertThrows(LectureAlreadyRegisteredException.class, () -> registrationService.isNotRegisteredYet(userId, lecture));
    }
}
