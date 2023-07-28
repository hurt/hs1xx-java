package net.draal.home.hs1xx.service;

import net.draal.home.hs1xx.apimodel.DeviceRequest;
import net.draal.home.hs1xx.service.impl.Hs1xxPayloadEncoder;

/**
 * Encoder/Decoder for device communication
 */
public interface PayloadEncoder {
    /**
     * @return Default implementation of this interface
     */
    static PayloadEncoder getDefault() {
        return new Hs1xxPayloadEncoder();
    }

    /**
     * Encodes the given string to a low-level device request
     *
     * @param message Given string request
     * @return the low-level request container
     */
    DeviceRequest encodeRequest(String message);

    /**
     * Decodes payload
     *
     * @param payload Raw payload as returned from the device
     * @return Decoded payload as String
     */
    String decode(byte[] payload);

    /**
     * Decodes header
     *
     * @param header Header to be decoded
     * @return header value (length of the payload)
     */
    int readHeader(byte[] header);
}
