package net.draal.home.hs1xx.apimodel.model.emeter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import net.draal.home.hs1xx.apimodel.model.shared.NextAction;

@Builder
public record RealtimeCommand(
        @JsonProperty("err_code") Integer errCode,
        @JsonProperty("next_action") NextAction nextAction,
        @JsonProperty("voltage_mv") Integer voltageMv,
        @JsonProperty("current_ma") Integer currentMa,
        @JsonProperty("power_mw") Integer powerMw,
        @JsonProperty("total_wh") Integer totalWh
) {
    public static RealtimeCommand empty() {
        return RealtimeCommand.builder().build();
    }
}
