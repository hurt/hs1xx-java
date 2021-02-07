package net.draal.home.hs1xx.apimodel.model.emeter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EnergyDay {
    private Integer year;
    private Integer month;
    private Integer day;
    @JsonProperty("energy_wh")
    private Integer energyWh;
}
