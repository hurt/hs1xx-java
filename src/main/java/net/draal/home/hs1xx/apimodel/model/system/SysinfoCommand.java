package net.draal.home.hs1xx.apimodel.model.system;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import net.draal.home.hs1xx.apimodel.model.shared.AbstractCommand;

@Data
public class SysinfoCommand extends AbstractCommand {
    @JsonProperty("sw_ver")
    private String swVer;

    @JsonProperty("hw_ver")
    private String hwVer;
    private String model;
    private String deviceId;
    private String oemId;
    private String hwId;
    private Integer rssi;
    @JsonProperty("longitude_i")
    private Integer longitude;
    @JsonProperty("latitude_i")
    private Integer latitude;
    private String alias;
    private String status;

    @JsonProperty("mic_type")
    private String micType;
    private String feature;
    private String mac;
    private Integer updating;
    @JsonProperty("led_off")
    private Integer ledOff;
    @JsonProperty("relay_state")
    private Integer relayState;
    @JsonProperty("on_time")
    private Integer onTime;
    @JsonProperty("active_mode")
    private String activeMode;
    @JsonProperty("icon_hash")
    private String iconHash;
    @JsonProperty("dev_name")
    private String devName;
}
