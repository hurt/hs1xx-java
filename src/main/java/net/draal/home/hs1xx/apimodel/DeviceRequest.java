package net.draal.home.hs1xx.apimodel;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.apache.commons.codec.binary.Hex;

import java.util.Arrays;

@EqualsAndHashCode
public class DeviceRequest {
    @NonNull
    private final byte[] header;
    @NonNull
    private final byte[] payload;

    DeviceRequest(DeviceRequestBuilder builder) {
        this.header = builder.header;
        this.payload = builder.payload;
    }

    public static DeviceRequestBuilder builder() {
        return new DeviceRequestBuilder();
    }

    @Override
    public String toString() {
        return "DeviceRequest{" +
                "header=0x" + Hex.encodeHexString(header) +
                ", payload=0x" + Hex.encodeHexString(payload) +
                ", reqlen=" + (payload.length + header.length) +
                '}';
    }

    public @NonNull byte[] getHeader() {
        return Arrays.copyOf(this.header, this.header.length);
    }

    public @NonNull byte[] getPayload() {
        return Arrays.copyOf(this.payload, this.payload.length);
    }

    public static class DeviceRequestBuilder {
        private @NonNull byte[] header;
        private @NonNull byte[] payload;

        DeviceRequestBuilder() {
        }

        public DeviceRequestBuilder header(@NonNull byte[] header) {
            this.header = Arrays.copyOf(header, header.length);
            return this;
        }

        public DeviceRequestBuilder payload(@NonNull byte[] payload) {
            this.payload = Arrays.copyOf(payload, payload.length);
            return this;
        }

        public DeviceRequest build() {
            return new DeviceRequest(this);
        }
    }
}
