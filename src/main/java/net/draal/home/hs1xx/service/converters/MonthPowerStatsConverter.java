package net.draal.home.hs1xx.service.converters;

import com.google.common.base.Preconditions;
import net.draal.home.hs1xx.apimodel.model.CommandContainer;
import net.draal.home.hs1xx.apimodel.model.emeter.EnergyDay;
import net.draal.home.hs1xx.apimodel.model.emeter.MonthstatCommand;
import net.draal.home.hs1xx.service.CommandResponseConverter;
import net.draal.home.hs1xx.service.data.MonthPowerStats;
import net.draal.home.hs1xx.service.data.TimePowerStats;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class MonthPowerStatsConverter implements CommandResponseConverter<MonthPowerStats> {
    @Override
    public MonthPowerStats convert(CommandContainer commandContainer) {
        Preconditions.checkArgument(commandContainer.getEmeter() != null);
        Preconditions.checkArgument(commandContainer.getEmeter().getMonthstatCommand() != null);
        MonthstatCommand monthstatCommand = commandContainer.getEmeter().getMonthstatCommand();
        return MonthPowerStats.builder()
                .months(asTimePowerStats(monthstatCommand.getMonthList()))
                .build();
    }

    private List<TimePowerStats> asTimePowerStats(List<EnergyDay> energyDays) {
        return CollectionUtils.emptyIfNull(energyDays).stream()
                .map(ConverterUtil::asTimePowerStats)
                .collect(Collectors.toList());
    }
}
