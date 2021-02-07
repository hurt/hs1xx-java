package net.draal.home.hs1xx.apimodel.model.system;

import lombok.Data;
import net.draal.home.hs1xx.apimodel.model.shared.AbstractCommand;

@Data
public class LedStateCommand extends AbstractCommand {
    private Integer off;
}
