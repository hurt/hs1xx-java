package net.draal.home.hs1xx.service;

import net.draal.home.hs1xx.service.impl.DefaultDeviceSocketFactory;

import java.io.IOException;
import java.net.Socket;

/**
 * Factory for client sockets for device communication
 */
public interface DeviceSocketFactory {
    /**
     * Creates a client socket
     *
     * @param device Device to connect to
     * @return A client socket
     * @throws IOException for any connection error
     * @throws IllegalArgumentException if the given device is null
     */
    Socket createSocket(Device device) throws IOException;

    /**
     * @return Default implementation of this interface
     */
    static DeviceSocketFactory getDefault() {
        return new DefaultDeviceSocketFactory();
    }
}
