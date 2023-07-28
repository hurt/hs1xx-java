package net.draal.home.hs1xx.service.impl;

import lombok.*;
import net.draal.home.hs1xx.service.Device;

@Builder(builderClassName = "Hs1xxDeviceBuilder")
public record Hs1xxDevice(
        @NonNull String host,
        int port,
        int socketTimeout
) implements Device {
    private static final int DEFAULT_PORT = 9999;
    private static final int DEFAULT_TIMEOUT = 10_000;

    public static class Hs1xxDeviceBuilder {
        private int port = DEFAULT_PORT;
        private int socketTimeout = DEFAULT_TIMEOUT;
    }
}
