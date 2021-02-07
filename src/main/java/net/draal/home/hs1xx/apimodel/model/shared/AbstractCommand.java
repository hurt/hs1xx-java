package net.draal.home.hs1xx.apimodel.model.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public abstract class AbstractCommand {
    @JsonProperty("err_code")
    private Integer errCode;
    @JsonProperty("next_action")
    private NextAction nextAction;
}
