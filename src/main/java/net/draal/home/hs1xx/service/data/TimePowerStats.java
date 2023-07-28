package net.draal.home.hs1xx.service.data;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TimePowerStats(
        LocalDate date,
        Double energy
) {
}
