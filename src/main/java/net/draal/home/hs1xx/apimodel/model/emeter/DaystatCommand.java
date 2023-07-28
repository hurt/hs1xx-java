package net.draal.home.hs1xx.apimodel.model.emeter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record DaystatCommand(
        Integer month,
        Integer year,
        @JsonProperty("day_list") List<EnergyDay> dayList
) {
}
