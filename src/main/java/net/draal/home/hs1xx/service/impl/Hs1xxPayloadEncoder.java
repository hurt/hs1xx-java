package net.draal.home.hs1xx.service.impl;

import net.draal.home.hs1xx.apimodel.DeviceRequest;
import net.draal.home.hs1xx.service.PayloadEncoder;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Hs1xxPayloadEncoder implements PayloadEncoder {
    private static final byte INITIALIZATION_VECTOR = (byte) 171;
    private static final int HEADER_SIZE = 4;

    @Override
    public DeviceRequest encodeRequest(String message) {
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        return DeviceRequest.builder()
                .header(createHeader(messageBytes))
                .payload(encodeBytes(messageBytes))
                .build();
    }

    @Override
    public String decode(byte[] messsage) {
        return new String(decodeBytes(messsage), StandardCharsets.UTF_8);
    }

    @Override
    public int readHeader(byte[] header) {
        if (header.length != HEADER_SIZE) {
            throw new IllegalArgumentException("Provided header data does not match expected size.");
        }

        return ByteBuffer.wrap(header).getInt();
    }

    private byte[] createHeader(byte[] message) {
        return ByteBuffer.allocate(HEADER_SIZE)
                .putInt(message.length)
                .array();
    }


    private byte[] encodeBytes(final byte[] plaintext) {
        byte key = INITIALIZATION_VECTOR;
        byte[] encodedData = new byte[plaintext.length];
        for (int i = 0; i < plaintext.length; i++) {
            byte encodedByte = (byte) (key ^ plaintext[i]);
            key = encodedByte;
            encodedData[i] = encodedByte;
        }
        return encodedData;
    }

    private byte[] decodeBytes(byte[] encoded) {
        byte key = INITIALIZATION_VECTOR;
        byte[] decodedData = new byte[encoded.length];
        for (int i = 0; i < encoded.length; i++) {
            byte decodedByte = (byte) (key ^ encoded[i]);
            key = encoded[i];
            decodedData[i] = decodedByte;
        }
        return decodedData;
    }
}
