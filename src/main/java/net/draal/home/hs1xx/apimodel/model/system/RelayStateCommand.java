package net.draal.home.hs1xx.apimodel.model.system;

import lombok.Data;
import net.draal.home.hs1xx.apimodel.model.shared.AbstractCommand;

@Data
public class RelayStateCommand extends AbstractCommand {
    private Integer state;
}
