package com.abab.lectureRegister.lecture;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LectureServiceTest {
    @Mock
    private LectureRepository lectureRepository;

    @InjectMocks
    private LectureService lectureService;

    @Test
    @DisplayName("특강 신청 가능 목록 조회")
    void 특강_날짜가_일치하고_현재_신청_인원이_30_미만인_특강_조회_성공(){
        // given
        LocalDateTime targetDate = LocalDateTime.of(2024, 12, 1, 10, 0);
        Lecture lecture = Lecture.builder()
                .lectureId(1L)
                .lectureName("Java 핵심 파헤치기")
                .startTime(targetDate)
                .currentEnrollment(20)
                .build();
        when(lectureRepository.findByStartTimeAndCurrentEnrollmentLessThan(targetDate, 30))
                .thenReturn(List.of(lecture));

        // when
        List<Lecture> result = lectureRepository.findByStartTimeAndCurrentEnrollmentLessThan(targetDate, 30);

        // then
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getLectureId());
        assertEquals(20, result.get(0).getCurrentEnrollment());
    }
}
