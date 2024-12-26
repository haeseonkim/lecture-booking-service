package com.abab.lectureRegister.lecture;

import com.abab.lectureRegister.exception.LectureFullException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;

    private static final int MAX_ENROLLMENT = 30;

    public List<Lecture> getAllLectures(LocalDateTime startDateTime) {
        return lectureRepository.findByStartTimeAndCurrentEnrollmentLessThan(startDateTime, MAX_ENROLLMENT);
    }

    public Lecture getLectureById(Long lectureId) {
        return lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("Lecture not found"));
    }

    public void isPossibleToRegister(Long lectureId) {
        if(lectureRepository.existsByLectureIdAndCurrentEnrollmentGreaterThanEqual(lectureId, MAX_ENROLLMENT)){
            throw new LectureFullException(lectureId);
        }
    }

    public void updateCurrentEnrollment(Lecture lecture) {
        lecture.upCurrentEnrollment();
        lectureRepository.save(lecture);
    }
}
