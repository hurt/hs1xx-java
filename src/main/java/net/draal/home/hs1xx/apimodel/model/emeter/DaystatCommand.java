package net.draal.home.hs1xx.apimodel.model.emeter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DaystatCommand {
    private Integer month;
    private Integer year;
    @JsonProperty("day_list")
    private List<EnergyDay> dayList;
}
