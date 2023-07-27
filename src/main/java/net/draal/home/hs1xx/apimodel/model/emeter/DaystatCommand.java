package net.draal.home.hs1xx.apimodel.model.emeter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

@Builder
public record DaystatCommand(
        @JsonProperty("month") Integer month,
        @JsonProperty("year") Integer year,
        @JsonProperty("day_list") List<EnergyDay> dayList
) {
    public List<EnergyDay> dayList() {
        return dayList != null ? List.copyOf(dayList) : null;
    }
}
