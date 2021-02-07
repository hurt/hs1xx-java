package net.draal.home.hs1xx.apimodel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import net.draal.home.hs1xx.apimodel.model.cloud.CloudInfoCommand;

@Data
public class CloudContainer {
    @JsonProperty("get_info")
    private CloudInfoCommand cloudInfoCommand;
}
