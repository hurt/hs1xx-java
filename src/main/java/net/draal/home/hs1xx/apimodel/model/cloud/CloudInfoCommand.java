package net.draal.home.hs1xx.apimodel.model.cloud;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import net.draal.home.hs1xx.apimodel.model.shared.AbstractCommand;

@Data
public class CloudInfoCommand extends AbstractCommand {
    private String username;
    private String server;
    private Integer binded;
    @JsonProperty("cld_connection")
    private Integer cldConnection;
    private Integer illegalType;
    private Integer stopConnect;
    private Integer tcspStatus;
    private String fwDlPage;
    private String tcspInfo;
    private Integer fwNotifyType;
}
