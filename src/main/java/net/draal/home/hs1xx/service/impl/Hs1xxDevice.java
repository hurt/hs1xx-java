package net.draal.home.hs1xx.service.impl;

import lombok.*;
import net.draal.home.hs1xx.service.Device;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class Hs1xxDevice implements Device {
    private static final int DEFAULT_PORT = 9999;
    private static final int DEFAULT_TIMEOUT = 10_000;

    @NonNull
    private final String host;
    @Builder.Default
    private final int port = DEFAULT_PORT;
    @Builder.Default
    private final int socketTimeout = DEFAULT_TIMEOUT;
}
