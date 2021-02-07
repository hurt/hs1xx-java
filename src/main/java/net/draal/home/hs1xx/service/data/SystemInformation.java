package net.draal.home.hs1xx.service.data;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Duration;

@Getter
@ToString
@EqualsAndHashCode
@Builder
public class SystemInformation {
    private final String alias;
    private final VendorProperties vendorProperties;
    private final Location location;
    private final DeviceState deviceState;
    private final NetworkInfo networkInfo;

    @Getter
    @ToString
    @EqualsAndHashCode
    @Builder
    public static class NetworkInfo {
        private final String macAddress;
        private final Integer rssi;
    }

    @Getter
    @ToString
    @EqualsAndHashCode
    @Builder
    public static class DeviceState {
        private final String status;
        private final Boolean updating;
        private final Boolean ledDisabled;
        private final Boolean relayEnabled;
        private final Duration relayEnabledSince;
        private final String activeMode;
        private final String iconHash;
    }

    @Getter
    @ToString
    @EqualsAndHashCode
    @Builder
    public static class VendorProperties {
        private final String firmwareVersion;
        private final String hardwareVersion;
        private final String model;
        private final String deviceId;
        private final String oemId;
        private final String hardwareId;
        private final String micType;
        private final String feature;
        private final String deviceName;

    }

    @Getter
    @ToString
    @EqualsAndHashCode
    @Builder
    public static class Location {
        private final Double latitude;
        private final Double longitude;
    }
}
