package net.draal.home.hs1xx.apimodel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import net.draal.home.hs1xx.apimodel.model.cloud.CloudInfoCommand;

@Builder
public record CloudContainer(
        @JsonProperty("get_info") CloudInfoCommand cloudInfoCommand
) {
    public static CloudContainer of(CloudInfoCommand cloudInfoCommand) {
        return CloudContainer.builder().cloudInfoCommand(cloudInfoCommand).build();
    }
}
