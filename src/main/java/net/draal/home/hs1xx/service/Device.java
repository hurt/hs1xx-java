package net.draal.home.hs1xx.service;

/**
 * Represents a device
 */
public interface Device {
    /**
     * @return host (name or IP) of the device
     */
    String host();

    /**
     * @return port number on which the device listens to
     */
    int port();

    /**
     * @return Socket timeout in ms
     */
    int socketTimeout();
}
