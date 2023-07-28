package net.draal.home.hs1xx.service.data;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
public record MonthPowerStats(
        List<TimePowerStats> months
) {
}
