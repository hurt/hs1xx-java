package net.draal.home.hs1xx.service.converters;

import com.google.common.base.Preconditions;
import net.draal.home.hs1xx.apimodel.model.CommandContainer;
import net.draal.home.hs1xx.apimodel.model.emeter.RealtimeCommand;
import net.draal.home.hs1xx.service.CommandResponseConverter;
import net.draal.home.hs1xx.service.data.RealtimePowerStats;

public class RealtimePowerStatsConverter implements CommandResponseConverter<RealtimePowerStats> {
    @Override
    public RealtimePowerStats convert(CommandContainer commandContainer) {
        Preconditions.checkArgument(commandContainer.getEmeter() != null);
        Preconditions.checkArgument(commandContainer.getEmeter().getRealtimeCommand() != null);
        RealtimeCommand emeterData = commandContainer.getEmeter().getRealtimeCommand();
        return RealtimePowerStats.builder()
                .power(ConverterUtil.asScaledDouble(emeterData.getPowerMw(), -3))
                .current(ConverterUtil.asScaledDouble(emeterData.getCurrentMa(), -3))
                .voltage(ConverterUtil.asScaledDouble(emeterData.getVoltageMv(), -3))
                .totalEnergy(ConverterUtil.asDouble(emeterData.getTotalWh()))
                .build();
    }
}
