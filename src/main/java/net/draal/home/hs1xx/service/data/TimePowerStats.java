package net.draal.home.hs1xx.service.data;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Builder
public record TimePowerStats(
        LocalDate date,
        Double energy
) {
}
