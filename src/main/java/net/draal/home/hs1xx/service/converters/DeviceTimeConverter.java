package net.draal.home.hs1xx.service.converters;

import com.google.common.base.Preconditions;
import net.draal.home.hs1xx.apimodel.model.CommandContainer;
import net.draal.home.hs1xx.apimodel.model.time.GetTimeCommand;
import net.draal.home.hs1xx.service.CommandResponseConverter;
import net.draal.home.hs1xx.service.data.DeviceTime;

import java.time.LocalDate;
import java.time.LocalTime;

public class DeviceTimeConverter implements CommandResponseConverter<DeviceTime> {
    @Override
    public DeviceTime convert(CommandContainer commandContainer) {
        Preconditions.checkArgument(commandContainer.time() != null);
        Preconditions.checkArgument(commandContainer.time().getTimeCommand() != null);

        var timeCommand = commandContainer.time().getTimeCommand();

        return DeviceTime.builder()
                .date(convertLocalDate(timeCommand))
                .time(convertLocalTime(timeCommand))
                .build();
    }

    private LocalDate convertLocalDate(GetTimeCommand getTimeCommand) {
        return LocalDate.of(getTimeCommand.year(), getTimeCommand.month(), getTimeCommand.mday());
    }

    private LocalTime convertLocalTime(GetTimeCommand getTimeCommand) {
        return LocalTime.of(getTimeCommand.hour(), getTimeCommand.min(), getTimeCommand.sec());
    }
}
