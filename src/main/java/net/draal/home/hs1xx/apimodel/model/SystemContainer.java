package net.draal.home.hs1xx.apimodel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import net.draal.home.hs1xx.apimodel.model.system.LedStateCommand;
import net.draal.home.hs1xx.apimodel.model.system.RelayStateCommand;
import net.draal.home.hs1xx.apimodel.model.system.SysinfoCommand;

@Builder
public record SystemContainer(
        @JsonProperty("get_sysinfo") SysinfoCommand sysinfoCommand,
        @JsonProperty("set_relay_state") RelayStateCommand relayStateCommand,
        @JsonProperty("set_led_off") LedStateCommand ledStateCommand
) {
    public static SystemContainer of(SysinfoCommand sysinfoCommand) {
        return SystemContainer.builder().sysinfoCommand(sysinfoCommand).build();
    }

    public static SystemContainer of(RelayStateCommand relayStateCommand) {
        return SystemContainer.builder().relayStateCommand(relayStateCommand).build();
    }

    public static SystemContainer of(LedStateCommand ledStateCommand) {
        return SystemContainer.builder().ledStateCommand(ledStateCommand).build();
    }
}
