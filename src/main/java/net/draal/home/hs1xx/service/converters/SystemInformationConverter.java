package net.draal.home.hs1xx.service.converters;

import com.google.common.base.Preconditions;
import net.draal.home.hs1xx.apimodel.model.CommandContainer;
import net.draal.home.hs1xx.apimodel.model.system.SysinfoCommand;
import net.draal.home.hs1xx.service.CommandResponseConverter;
import net.draal.home.hs1xx.service.data.SystemInformation;

public class SystemInformationConverter implements CommandResponseConverter<SystemInformation> {
    private static final int COORDINATE_SCALING_EXPONENT = -4;

    @Override
    public SystemInformation convert(CommandContainer commandContainer) {
        Preconditions.checkArgument(commandContainer.system() != null);
        Preconditions.checkArgument(commandContainer.system().sysinfoCommand() != null);
        SysinfoCommand sysinfoCommand = commandContainer.system().sysinfoCommand();
        return SystemInformation.builder()
                .alias(sysinfoCommand.alias())
                .vendorProperties(getVendorProperties(sysinfoCommand))
                .location(getLocation(sysinfoCommand))
                .deviceState(getDeviceState(sysinfoCommand))
                .networkInfo(getNetworkInfo(sysinfoCommand))
                .build();
    }

    private SystemInformation.NetworkInfo getNetworkInfo(SysinfoCommand sysinfoCommand) {
        return SystemInformation.NetworkInfo.builder()
                .macAddress(sysinfoCommand.mac())
                .rssi(sysinfoCommand.rssi())
                .build();
    }

    private SystemInformation.DeviceState getDeviceState(SysinfoCommand sysinfoCommand) {
        return SystemInformation.DeviceState.builder()
                .status(sysinfoCommand.status())
                .updating(ConverterUtil.asBoolean(sysinfoCommand.updating()))
                .ledDisabled(ConverterUtil.asBoolean(sysinfoCommand.ledOff()))
                .relayEnabled(ConverterUtil.asBoolean(sysinfoCommand.relayState()))
                .relayEnabledSince(ConverterUtil.asDuration(sysinfoCommand.onTime()))
                .activeMode(sysinfoCommand.activeMode())
                .iconHash(sysinfoCommand.iconHash())
                .build();
    }

    private SystemInformation.VendorProperties getVendorProperties(SysinfoCommand sysinfoCommand) {
        return SystemInformation.VendorProperties.builder()
                .firmwareVersion(sysinfoCommand.swVer())
                .hardwareVersion(sysinfoCommand.hwVer())
                .model(sysinfoCommand.model())
                .deviceId(sysinfoCommand.deviceId())
                .oemId(sysinfoCommand.oemId())
                .hardwareId(sysinfoCommand.hwId())
                .micType(sysinfoCommand.micType())
                .feature(sysinfoCommand.feature())
                .deviceName(sysinfoCommand.devName())
                .build();
    }

    private SystemInformation.Location getLocation(SysinfoCommand sysinfoCommand) {
        return SystemInformation.Location.builder()
                .latitude(asCoordinate(sysinfoCommand.latitude()))
                .longitude(asCoordinate(sysinfoCommand.longitude()))
                .build();
    }

    private Double asCoordinate(Integer value) {
        return ConverterUtil.asScaledDouble(value, COORDINATE_SCALING_EXPONENT);
    }
}
