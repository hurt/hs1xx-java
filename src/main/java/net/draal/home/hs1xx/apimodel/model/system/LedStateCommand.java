package net.draal.home.hs1xx.apimodel.model.system;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import net.draal.home.hs1xx.apimodel.model.shared.NextAction;

@Builder
public record LedStateCommand(
        @JsonProperty("err_code") Integer errCode,
        @JsonProperty("next_action") NextAction nextAction,
        Integer off
) {
}
