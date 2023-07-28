package net.draal.home.hs1xx.service.data;

import lombok.Builder;

import java.time.Duration;

@Builder
public record SystemInformation(
        String alias,
        VendorProperties vendorProperties,
        Location location,
        DeviceState deviceState,
        NetworkInfo networkInfo
) {
    @Builder
    public record NetworkInfo(
            String macAddress,
            Integer rssi
    ) {
    }

    @Builder
    public record DeviceState(
            String status,
            Boolean updating,
            Boolean ledDisabled,
            Boolean relayEnabled,
            Duration relayEnabledSince,
            String activeMode,
            String iconHash
    ) {
    }

    @Builder
    public record VendorProperties(
            String firmwareVersion,
            String hardwareVersion,
            String model,
            String deviceId,
            String oemId,
            String hardwareId,
            String micType,
            String feature,
            String deviceName
    ) {
    }

    @Builder
    public record Location(
            Double latitude,
            Double longitude
    ) {
    }
}
