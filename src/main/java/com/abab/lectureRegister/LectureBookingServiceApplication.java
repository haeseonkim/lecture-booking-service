package com.abab.lectureRegister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LectureBookingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LectureBookingServiceApplication.class, args);
    }

}
