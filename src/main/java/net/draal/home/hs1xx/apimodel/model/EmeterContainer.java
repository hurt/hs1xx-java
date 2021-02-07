package net.draal.home.hs1xx.apimodel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import net.draal.home.hs1xx.apimodel.model.emeter.DaystatCommand;
import net.draal.home.hs1xx.apimodel.model.emeter.MonthstatCommand;
import net.draal.home.hs1xx.apimodel.model.emeter.RealtimeCommand;

@Data
public class EmeterContainer {
    @JsonProperty("get_realtime")
    private RealtimeCommand realtimeCommand;
    @JsonProperty("get_monthstat")
    private MonthstatCommand monthstatCommand;
    @JsonProperty("get_daystat")
    private DaystatCommand daystatCommand;
}
