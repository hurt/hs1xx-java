package net.draal.home.hs1xx.service.data;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
public record DeviceTime(
        LocalDate date,
        LocalTime time
) {
    public LocalDateTime asLocalDateTime() {
        return LocalDateTime.of(date, time);
    }
}
