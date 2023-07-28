package net.draal.home.hs1xx.apimodel.model.system;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import net.draal.home.hs1xx.apimodel.model.shared.NextAction;

@Builder
public record SysinfoCommand(
        @JsonProperty("err_code") Integer errCode,
        @JsonProperty("next_action") NextAction nextAction,
        @JsonProperty("sw_ver") String swVer,
        @JsonProperty("hw_ver") String hwVer,
        String model,
        String deviceId,
        String oemId,
        String hwId,
        Integer rssi,
        @JsonProperty("longitude_i") Integer longitude,
        @JsonProperty("latitude_i") Integer latitude,
        String alias,
        String status,
        @JsonProperty("mic_type") String micType,
        String feature,
        String mac,
        Integer updating,
        @JsonProperty("led_off") Integer ledOff,
        @JsonProperty("relay_state") Integer relayState,
        @JsonProperty("on_time") Integer onTime,
        @JsonProperty("active_mode") String activeMode,
        @JsonProperty("icon_hash") String iconHash,
        @JsonProperty("dev_name") String devName
) {
    public static SysinfoCommand empty() {
        return SysinfoCommand.builder().build();
    }
}
