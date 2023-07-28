package net.draal.home.hs1xx.service.data;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
public record RealtimePowerStats(
        Double voltage,
        Double current,
        Double power,
        Double totalEnergy
) {
}
