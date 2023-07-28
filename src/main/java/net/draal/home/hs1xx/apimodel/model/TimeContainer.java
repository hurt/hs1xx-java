package net.draal.home.hs1xx.apimodel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import net.draal.home.hs1xx.apimodel.model.time.GetTimeCommand;

@Builder
public record TimeContainer(
        @JsonProperty("get_time") GetTimeCommand getTimeCommand
) {
    public static TimeContainer of(GetTimeCommand getTimeCommand) {
        return TimeContainer.builder().getTimeCommand(getTimeCommand).build();
    }
}
