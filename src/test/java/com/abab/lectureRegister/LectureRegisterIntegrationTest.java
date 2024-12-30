package com.abab.lectureRegister;

import com.abab.lectureRegister.exception.custom.LectureAlreadyRegisteredException;
import com.abab.lectureRegister.model.Lecture;
import com.abab.lectureRegister.repository.LectureRepository;
import com.abab.lectureRegister.model.Registration;
import com.abab.lectureRegister.repository.RegistrationRepository;
import com.abab.lectureRegister.service.LectureRegisterServiceFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class LectureRegisterIntegrationTest {
    @Autowired
    private LectureRegisterServiceFacade lectureRegisterServiceFacade;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @BeforeEach
    void setUp() {
        // 테스트용 데이터 생성
        Lecture lecture = Lecture.builder()
                .lectureName("테스트 특강")
                .startDateTime(LocalDateTime.now().plusDays(1))
                .currentEnrollment(0)
                .lecturer("강사")
                .build();
        lectureRepository.save(lecture);
    }

    @Test
    void 동시에_40명이_같은_특강_수강_신청() throws Exception {
        // given
        final int THREAD_COUNT = 40;
        final Long LECTURE_ID = 1L;

        // 특강 데이터 미리 생성
        Lecture lecture = Lecture.builder()
                .lectureName("테스트 특강")
                .startDateTime(LocalDateTime.now().plusDays(1))
                .currentEnrollment(0)
                .lecturer("강사")
                .build();
        lectureRepository.save(lecture);

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(1);
        AtomicInteger successCount = new AtomicInteger();

        // when
        // 40개의 Future를 저장할 리스트
        List<Future<Boolean>> futures = new ArrayList<>();

        // 40개의 요청을 동시에 실행
        for (int i = 0; i < THREAD_COUNT; i++) {
            final long userId = i + 1; // 각각 다른 userId

            Future<Boolean> future = executorService.submit(() -> {
                try {
                    latch.await(); // 모든 쓰레드가 ready 상태가 될 때까지 대기
                    try {
                        lectureRegisterServiceFacade.registerLecture(userId, LECTURE_ID);
                        successCount.incrementAndGet();
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                } catch (InterruptedException e) {
                    return false;
                }
            });
            futures.add(future);
        }

        // 모든 쓰레드가 준비된 후 동시 실행
        latch.countDown();

        // then
        // 모든 Future의 결과 수집
        List<Boolean> results = futures.stream()
                .map(future -> {
                    try {
                        return future.get(10, TimeUnit.SECONDS);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .toList();

        // 검증
        Lecture updatedLecture = lectureRepository.findById(LECTURE_ID).orElseThrow();
        assertEquals(30, updatedLecture.getCurrentEnrollment()); // 최대 30명만 등록되어야 함
        assertEquals(30, successCount.get()); // 성공 건수도 30이어야 함

        executorService.shutdown();
    }

    @Test
    void 한명의_유저가_같은_특강_5번_신청시_1번만_성공() throws Exception {
        // given
        final Long USER_ID = 1L;
        final int REQUEST_COUNT = 5;

        // 테스트용 특강 데이터 생성
        Lecture lecture = Lecture.builder()
                .lectureName("테스트 특강")
                .startDateTime(LocalDateTime.now().plusDays(1))
                .currentEnrollment(0)
                .lecturer("강사")
                .build();
        lectureRepository.save(lecture);
        final Long LECTURE_ID = lecture.getLectureId();

        // 동시 요청 처리를 위한 설정
        ExecutorService executorService = Executors.newFixedThreadPool(REQUEST_COUNT);
        CountDownLatch latch = new CountDownLatch(1);
        AtomicInteger successCount = new AtomicInteger();

        // when
        List<Future<Boolean>> futures = new ArrayList<>();

        // 동일한 유저로 5번 요청
        for (int i = 0; i < REQUEST_COUNT; i++) {
            Future<Boolean> future = executorService.submit(() -> {
                try {
                    latch.await(); // 모든 쓰레드가 준비될 때까지 대기
                    try {
                        lectureRegisterServiceFacade.registerLecture(USER_ID, LECTURE_ID);
                        successCount.incrementAndGet(); // 성공 시 카운트 증가
                        return true;
                    } catch (LectureAlreadyRegisteredException e) {
                        return false;
                    }
                } catch (InterruptedException e) {
                    return false;
                }
            });
            futures.add(future);
        }

        latch.countDown(); // 모든 쓰레드 동시 실행

        // then
        // 모든 Future의 결과 수집
        List<Boolean> results = futures.stream()
                .map(future -> {
                    try {
                        return future.get(10, TimeUnit.SECONDS);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .toList();

        // 검증
        Lecture updatedLecture = lectureRepository.findById(LECTURE_ID).orElseThrow();
        assertEquals(1, updatedLecture.getCurrentEnrollment()); // 한 번만 등록되어야 함
        assertEquals(1, successCount.get()); // 성공 건수도 1이어야 함

        // DB에 실제로 한 건만 저장되었는지 확인
        List<Registration> registrations = registrationRepository.findAllByUserIdAndLecture(USER_ID, lecture);
        assertEquals(1, registrations.size());

        executorService.shutdown();
    }

}
