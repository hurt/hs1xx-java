package net.draal.home.hs1xx.apimodel.model.time;

import lombok.Data;
import net.draal.home.hs1xx.apimodel.model.shared.AbstractCommand;

@Data
public class GetTimeCommand extends AbstractCommand {
    private Integer year;
    private Integer month;
    private Integer mday;
    private Integer hour;
    private Integer min;
    private Integer sec;
}
