package net.draal.home.hs1xx.service.data;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@Builder
public class RealtimePowerStats {
    private final Double voltage;
    private final Double current;
    private final Double power;
    private final Double totalEnergy;
}
