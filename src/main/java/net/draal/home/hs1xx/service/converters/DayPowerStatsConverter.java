package net.draal.home.hs1xx.service.converters;

import com.google.common.base.Preconditions;
import net.draal.home.hs1xx.apimodel.model.CommandContainer;
import net.draal.home.hs1xx.apimodel.model.emeter.DaystatCommand;
import net.draal.home.hs1xx.apimodel.model.emeter.EnergyDay;
import net.draal.home.hs1xx.service.CommandResponseConverter;
import net.draal.home.hs1xx.service.data.DayPowerStats;
import net.draal.home.hs1xx.service.data.TimePowerStats;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class DayPowerStatsConverter implements CommandResponseConverter<DayPowerStats> {
    @Override
    public DayPowerStats convert(CommandContainer commandContainer) {
        Preconditions.checkArgument(commandContainer.getEmeter() != null);
        Preconditions.checkArgument(commandContainer.getEmeter().getDaystatCommand() != null);
        DaystatCommand daystatCommand = commandContainer.getEmeter().getDaystatCommand();
        return DayPowerStats.builder()
                .days(asTimePowerStats(daystatCommand.dayList()))
                .build();
    }

    private List<TimePowerStats> asTimePowerStats(List<EnergyDay> energyDays) {
        return CollectionUtils.emptyIfNull(energyDays).stream()
                .map(ConverterUtil::asTimePowerStats)
                .collect(Collectors.toList());
    }
}
