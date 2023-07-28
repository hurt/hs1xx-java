package net.draal.home.hs1xx.service.data;

import lombok.Builder;

import java.util.List;

@Builder
public record MonthPowerStats(
        List<TimePowerStats> months
) {
}
