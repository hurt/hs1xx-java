package net.draal.home.hs1xx.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.draal.home.hs1xx.apimodel.DeviceRequest;
import net.draal.home.hs1xx.apimodel.model.CommandContainer;
import net.draal.home.hs1xx.service.Device;
import net.draal.home.hs1xx.service.DeviceClient;
import net.draal.home.hs1xx.service.DeviceSocketFactory;
import net.draal.home.hs1xx.service.PayloadEncoder;
import net.draal.home.hs1xx.service.exception.Hs1xxException;
import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

@Slf4j
public class Hs1xxClient implements DeviceClient {
    private static final int HEADER_LENGTH = 4;
    private final PayloadEncoder payloadEncoder;
    private final ObjectMapper objectMapper;
    private final DeviceSocketFactory deviceSocketFactory;

    public Hs1xxClient(PayloadEncoder payloadEncoder, ObjectMapper objectMapper, DeviceSocketFactory deviceSocketFactory) {
        this.payloadEncoder = payloadEncoder;
        this.objectMapper = objectMapper;
        this.deviceSocketFactory = deviceSocketFactory;
    }

    public CommandContainer send(Device device, CommandContainer commandRequest) {
        try {
            return sendInternal(device, commandRequest);
        } catch (RuntimeException | IOException e) {
            throw new Hs1xxException("Exception while trying to communicate with device.", e);
        }
    }

    private CommandContainer sendInternal(Device device, CommandContainer commandRequest) throws IOException {
        DeviceRequest requestData = mapAndConvert(commandRequest);
        LOG.debug("Sending request to device {}", device);
        try (Socket clientSocket = deviceSocketFactory.createSocket(device)) {
            send(clientSocket, requestData);
            byte[] responsePayload = receive(clientSocket);
            return mapAndConvert(responsePayload);
        }
    }

    private void send(Socket socket, DeviceRequest requestData) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(requestData.getHeader());
        outputStream.write(requestData.getPayload());
        outputStream.flush();
    }

    private byte[] receive(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        int responseLength = payloadEncoder.readHeader(inputStream.readNBytes(HEADER_LENGTH));
        LOG.trace("Expecting payload length of {} bytes", responseLength);
        return inputStream.readNBytes(responseLength);
    }

    private CommandContainer mapAndConvert(byte[] response) throws IOException {
        LOG.trace("Raw response: 0x{}", Hex.encodeHexString(response));
        String decodedResponse = payloadEncoder.decode(response);
        LOG.debug("Json response: {}", decodedResponse);
        return objectMapper.readValue(decodedResponse, CommandContainer.class);
    }

    private DeviceRequest mapAndConvert(CommandContainer commandContainer) throws IOException {
        String jsonRequest = objectMapper.writeValueAsString(commandContainer);
        LOG.debug("Json request: {}", jsonRequest);
        DeviceRequest deviceRequest = payloadEncoder.encodeRequest(jsonRequest);
        LOG.trace("Raw request: {}", deviceRequest);
        return deviceRequest;
    }
}
