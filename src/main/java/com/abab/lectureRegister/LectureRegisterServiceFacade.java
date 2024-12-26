package com.abab.lectureRegister;

import com.abab.lectureRegister.registration.RegistrationService;
import com.abab.lectureRegister.lecture.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureRegisterServiceFacade {
    private final LectureService lectureService;
    private final RegistrationService registrationService;

    // TODO: 1) 특강 신청 가능 목록 조회
    // - 성공: 특강 시작 날짜(startDateTime)와 일치하고, 현재 신청 인원(currentEnrollment)이 30 미만인 것만 조회

    // TODO: 2) 특강 신청
    // - 실패: 이미 신청한 특강이면 실패 Registration
    // - 실패: 현재 신청 인원(currentEnrollment)가 30 이상이면 실패 Lecture

    // TODO: 3) 특강 신청 완료 목록 조회
    // - 성공: 신청한 특강 목록을 특강 정보와 함께 조회
}
