package net.draal.home.hs1xx.apimodel.model.system;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import net.draal.home.hs1xx.apimodel.model.shared.NextAction;

@Builder
public record RelayStateCommand(
        @JsonProperty("err_code") Integer errCode,
        @JsonProperty("next_action") NextAction nextAction,
        Integer state
) {
    public static RelayStateCommand ofState(Integer state) {
        return RelayStateCommand.builder().state(state).build();
    }
}
