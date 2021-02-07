package net.draal.home.hs1xx.service;

/**
 * Represents a device
 */
public interface Device {
    /**
     * @return host (name or IP) of the device
     */
    String getHost();

    /**
     * @return port number on which the device listens to
     */
    int getPort();

    /**
     * @return Socket timeout in ms
     */
    int getSocketTimeout();
}
