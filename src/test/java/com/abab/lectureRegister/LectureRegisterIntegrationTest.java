package com.abab.lectureRegister;

import com.abab.lectureRegister.model.Lecture;
import com.abab.lectureRegister.repository.LectureRepository;
import com.abab.lectureRegister.service.LectureRegisterServiceFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
    private WebApplicationContext context;

    @Autowired
    private LectureRepository lectureRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

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

}
