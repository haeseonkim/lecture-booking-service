package com.abab.lectureRegister.repository;

import com.abab.lectureRegister.model.Lecture;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT l FROM Lecture l WHERE l.lectureId = :lectureId")
    Optional<Lecture> findByIdWithLock(@Param("lectureId") Long lectureId);

    List<Lecture> findByStartDateTimeAndCurrentEnrollmentLessThan(LocalDateTime startTime, int currentEnrollment);
}
