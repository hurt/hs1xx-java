package net.draal.home.hs1xx.apimodel.model.cloud;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import net.draal.home.hs1xx.apimodel.model.shared.NextAction;

@Builder
public record CloudInfoCommand(
        @JsonProperty("err_code") Integer errCode,
        @JsonProperty("next_action") NextAction nextAction,
        String username,
        String server,
        Integer binded,
        @JsonProperty("cld_connection") Integer cldConnection,
        Integer illegalType,
        Integer stopConnect,
        Integer tcspStatus,
        String fwDlPage,
        String tcspInfo,
        Integer fwNotifyType
) {
}
