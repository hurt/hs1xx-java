package net.draal.home.hs1xx.service.data;

import lombok.Builder;

@Builder
public record RealtimePowerStats(
        Double voltage,
        Double current,
        Double power,
        Double totalEnergy
) {
}
