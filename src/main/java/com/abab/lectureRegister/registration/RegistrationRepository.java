package com.abab.lectureRegister.registration;

import com.abab.lectureRegister.lecture.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    Optional<Registration> findByUserIdAndLecture(Long userId, Lecture lecture);

    @Query("SELECT r.lecture FROM Registration r WHERE r.userId = :userId")
    List<Lecture> findLecturesByUserId(@Param("userId") Long userId);

}
