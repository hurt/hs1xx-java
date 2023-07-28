package net.draal.home.hs1xx.apimodel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import net.draal.home.hs1xx.apimodel.model.emeter.DaystatCommand;
import net.draal.home.hs1xx.apimodel.model.emeter.MonthstatCommand;
import net.draal.home.hs1xx.apimodel.model.emeter.RealtimeCommand;

@Builder
public record EmeterContainer(
        @JsonProperty("get_realtime") RealtimeCommand realtimeCommand,
        @JsonProperty("get_monthstat") MonthstatCommand monthstatCommand,
        @JsonProperty("get_daystat") DaystatCommand daystatCommand
) {
    public static EmeterContainer of(RealtimeCommand realtimeCommand) {
        return EmeterContainer.builder().realtimeCommand(realtimeCommand).build();
    }

    public static EmeterContainer of(MonthstatCommand monthstatCommand) {
        return EmeterContainer.builder().monthstatCommand(monthstatCommand).build();
    }

    public static EmeterContainer of(DaystatCommand daystatCommand) {
        return EmeterContainer.builder().daystatCommand(daystatCommand).build();
    }
}
