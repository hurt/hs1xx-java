package net.draal.home.hs1xx.apimodel.model.time;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import net.draal.home.hs1xx.apimodel.model.shared.NextAction;

@Builder
public record GetTimeCommand(
        @JsonProperty("err_code") Integer errCode,
        @JsonProperty("next_action") NextAction nextAction,
        Integer year,
        Integer month,
        Integer mday,
        Integer hour,
        Integer min,
        Integer sec
) {
    public static GetTimeCommand empty() {
        return GetTimeCommand.builder().build();
    }
}
