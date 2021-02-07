package net.draal.home.hs1xx.apimodel.model.emeter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import net.draal.home.hs1xx.apimodel.model.shared.AbstractCommand;

import java.util.List;

@Data
public class MonthstatCommand extends AbstractCommand {
    private Integer year;
    @JsonProperty("month_list")
    private List<EnergyDay> monthList;
}
