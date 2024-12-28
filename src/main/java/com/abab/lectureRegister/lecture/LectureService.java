package com.abab.lectureRegister.lecture;

import com.abab.lectureRegister.exception.LectureFullException;
import com.abab.lectureRegister.exception.LectureNotFoundException;
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
        return lectureRepository.findByStartDateTimeAndCurrentEnrollmentLessThan(startDateTime, MAX_ENROLLMENT);
    }

    public Lecture getLectureByIdWithLock(Long lectureId) {
        return lectureRepository.findByIdWithLock(lectureId)
                .orElseThrow(() -> new LectureNotFoundException(lectureId));
    }

    public void isPossibleToRegister(Lecture lecture) {
        if(lecture.getCurrentEnrollment() >= MAX_ENROLLMENT){
            throw new LectureFullException(lecture.getLectureId());
        }
    }

    public void updateCurrentEnrollment(Lecture lecture) {
        lecture.upCurrentEnrollment();
        lectureRepository.save(lecture);
    }
}
