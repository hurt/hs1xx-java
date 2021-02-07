package net.draal.home.hs1xx.apimodel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import net.draal.home.hs1xx.apimodel.model.system.LedStateCommand;
import net.draal.home.hs1xx.apimodel.model.system.RelayStateCommand;
import net.draal.home.hs1xx.apimodel.model.system.SysinfoCommand;

@Data
public class SystemContainer {
    @JsonProperty("get_sysinfo")
    private SysinfoCommand sysinfoCommand;
    @JsonProperty("set_relay_state")
    private RelayStateCommand relayStateCommand;
    @JsonProperty("set_led_off")
    private LedStateCommand ledStateCommand;
}
