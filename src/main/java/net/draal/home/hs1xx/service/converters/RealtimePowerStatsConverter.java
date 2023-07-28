package net.draal.home.hs1xx.service.converters;

import com.google.common.base.Preconditions;
import net.draal.home.hs1xx.apimodel.model.CommandContainer;
import net.draal.home.hs1xx.apimodel.model.emeter.RealtimeCommand;
import net.draal.home.hs1xx.service.CommandResponseConverter;
import net.draal.home.hs1xx.service.data.RealtimePowerStats;

public class RealtimePowerStatsConverter implements CommandResponseConverter<RealtimePowerStats> {
    @Override
    public RealtimePowerStats convert(CommandContainer commandContainer) {
        Preconditions.checkArgument(commandContainer.emeter() != null);
        Preconditions.checkArgument(commandContainer.emeter().realtimeCommand() != null);
        RealtimeCommand emeterData = commandContainer.emeter().realtimeCommand();
        return RealtimePowerStats.builder()
                .power(ConverterUtil.asScaledDouble(emeterData.powerMw(), -3))
                .current(ConverterUtil.asScaledDouble(emeterData.currentMa(), -3))
                .voltage(ConverterUtil.asScaledDouble(emeterData.voltageMv(), -3))
                .totalEnergy(ConverterUtil.asDouble(emeterData.totalWh()))
                .build();
    }
}
