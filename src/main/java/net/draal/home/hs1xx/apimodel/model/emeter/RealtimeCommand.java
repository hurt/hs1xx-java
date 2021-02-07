package net.draal.home.hs1xx.apimodel.model.emeter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import net.draal.home.hs1xx.apimodel.model.shared.AbstractCommand;

@Data
public class RealtimeCommand extends AbstractCommand {
    @JsonProperty("voltage_mv")
    private Integer voltageMv;
    @JsonProperty("current_ma")
    private Integer currentMa;
    @JsonProperty("power_mw")
    private Integer powerMw;
    @JsonProperty("total_wh")
    private Integer totalWh;
}
