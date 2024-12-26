package com.abab.lectureRegister.lecture;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findByStartTimeAndCurrentEnrollmentLessThan(LocalDateTime startTime, int currentEnrollment);
    boolean existsByLectureIdAndCurrentEnrollmentGreaterThanEqual(Long lectureId, int currentEnrollment);
}
