package net.draal.home.hs1xx.service.converters;

import com.google.common.base.Preconditions;
import net.draal.home.hs1xx.apimodel.model.CommandContainer;
import net.draal.home.hs1xx.apimodel.model.time.GetTimeCommand;
import net.draal.home.hs1xx.service.CommandResponseConverter;
import net.draal.home.hs1xx.service.data.DeviceTime;

import java.time.LocalDateTime;

public class DeviceTimeConverter implements CommandResponseConverter<DeviceTime> {
    @Override
    public DeviceTime convert(CommandContainer commandContainer) {
        Preconditions.checkArgument(commandContainer.getTime() != null);
        Preconditions.checkArgument(commandContainer.getTime().getGetTimeCommand() != null);

        return DeviceTime.builder()
                .time(asLocalDateTime(commandContainer.getTime().getGetTimeCommand()))
                .build();
    }

    private LocalDateTime asLocalDateTime(GetTimeCommand getTimeCommand) {
        return LocalDateTime.of(getTimeCommand.getYear(), getTimeCommand.getMonth(), getTimeCommand.getMday(),
                getTimeCommand.getHour(), getTimeCommand.getMin(), getTimeCommand.getSec());
    }
}
