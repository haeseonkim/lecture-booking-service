package com.abab.lectureRegister.registration;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RegistrationResponse {
    private Long registerId;
    private Long lectureId;
    private Long userId;
    private LocalDateTime registrationTime;

    @Builder
    public RegistrationResponse(Registration registration) {
        this.registerId = registration.getRegisterId();
        this.lectureId = registration.getLecture().getLectureId();
        this.userId = registration.getUserId();
        this.registrationTime = registration.getRegistrationTime();
    }
}
