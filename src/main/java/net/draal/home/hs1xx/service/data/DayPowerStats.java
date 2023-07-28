package net.draal.home.hs1xx.service.data;

import lombok.Builder;

import java.util.List;

@Builder
public record DayPowerStats(
        List<TimePowerStats> days
) {
}
