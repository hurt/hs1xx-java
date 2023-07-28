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
        Preconditions.checkArgument(commandContainer.time() != null);
        Preconditions.checkArgument(commandContainer.time().getTimeCommand() != null);

        return DeviceTime.builder()
                .time(asLocalDateTime(commandContainer.time().getTimeCommand()))
                .build();
    }

    private LocalDateTime asLocalDateTime(GetTimeCommand getTimeCommand) {
        return LocalDateTime.of(getTimeCommand.year(), getTimeCommand.month(), getTimeCommand.mday(),
                getTimeCommand.hour(), getTimeCommand.min(), getTimeCommand.sec());
    }
}
