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
        Preconditions.checkArgument(commandContainer.getSystem() != null);
        Preconditions.checkArgument(commandContainer.getSystem().getSysinfoCommand() != null);
        SysinfoCommand sysinfoCommand = commandContainer.getSystem().getSysinfoCommand();
        return SystemInformation.builder()
                .alias(sysinfoCommand.getAlias())
                .vendorProperties(getVendorProperties(sysinfoCommand))
                .location(getLocation(sysinfoCommand))
                .deviceState(getDeviceState(sysinfoCommand))
                .networkInfo(getNetworkInfo(sysinfoCommand))
                .build();
    }

    private SystemInformation.NetworkInfo getNetworkInfo(SysinfoCommand sysinfoCommand) {
        return SystemInformation.NetworkInfo.builder()
                .macAddress(sysinfoCommand.getMac())
                .rssi(sysinfoCommand.getRssi())
                .build();
    }

    private SystemInformation.DeviceState getDeviceState(SysinfoCommand sysinfoCommand) {
        return SystemInformation.DeviceState.builder()
                .status(sysinfoCommand.getStatus())
                .updating(ConverterUtil.asBoolean(sysinfoCommand.getUpdating()))
                .ledDisabled(ConverterUtil.asBoolean(sysinfoCommand.getLedOff()))
                .relayEnabled(ConverterUtil.asBoolean(sysinfoCommand.getRelayState()))
                .relayEnabledSince(ConverterUtil.asDuration(sysinfoCommand.getOnTime()))
                .activeMode(sysinfoCommand.getActiveMode())
                .iconHash(sysinfoCommand.getIconHash())
                .build();
    }

    private SystemInformation.VendorProperties getVendorProperties(SysinfoCommand sysinfoCommand) {
        return SystemInformation.VendorProperties.builder()
                .firmwareVersion(sysinfoCommand.getSwVer())
                .hardwareVersion(sysinfoCommand.getHwVer())
                .model(sysinfoCommand.getModel())
                .deviceId(sysinfoCommand.getDeviceId())
                .oemId(sysinfoCommand.getOemId())
                .hardwareId(sysinfoCommand.getHwId())
                .micType(sysinfoCommand.getMicType())
                .feature(sysinfoCommand.getFeature())
                .deviceName(sysinfoCommand.getDevName())
                .build();
    }

    private SystemInformation.Location getLocation(SysinfoCommand sysinfoCommand) {
        return SystemInformation.Location.builder()
                .latitude(asCoordinate(sysinfoCommand.getLatitude()))
                .longitude(asCoordinate(sysinfoCommand.getLongitude()))
                .build();
    }

    private Double asCoordinate(Integer value) {
        return ConverterUtil.asScaledDouble(value, COORDINATE_SCALING_EXPONENT);
    }
}
