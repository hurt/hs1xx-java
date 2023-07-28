package net.draal.home.hs1xx.apimodel.model.emeter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record EnergyDay(
        Integer year,
        Integer month,
        Integer day,
        @JsonProperty("energy_wh") Integer energyWh
) {
}
