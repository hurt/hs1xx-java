package net.draal.home.hs1xx.apimodel.model.emeter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import net.draal.home.hs1xx.apimodel.model.shared.NextAction;

import java.util.List;

@Builder
public record MonthstatCommand(
        @JsonProperty("err_code") Integer errCode,
        @JsonProperty("next_action") NextAction nextAction,
        Integer year,
        @JsonProperty("month_list") List<EnergyDay> monthList
) {
    public static MonthstatCommand ofYear(Integer year) {
        return MonthstatCommand.builder().year(year).build();
    }
}
