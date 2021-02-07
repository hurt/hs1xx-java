package net.draal.home.hs1xx.apimodel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import net.draal.home.hs1xx.apimodel.model.time.GetTimeCommand;

@Data
public class TimeContainer {
    @JsonProperty("get_time")
    private GetTimeCommand getTimeCommand;
}
