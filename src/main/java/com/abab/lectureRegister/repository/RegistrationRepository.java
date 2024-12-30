package com.abab.lectureRegister.repository;

import com.abab.lectureRegister.model.Lecture;
import com.abab.lectureRegister.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    Optional<Registration> findByUserIdAndLecture(Long userId, Lecture lecture);

    List<Registration> findAllByUserIdAndLecture(Long userId, Lecture lecture);

    @Query("select r.lecture from Registration r where r.userId = :userId")
    List<Lecture> findLecturesByUserId(@Param("userId") Long userId);
}
