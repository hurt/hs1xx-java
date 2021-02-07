package net.draal.home.hs1xx.service;

import net.draal.home.hs1xx.apimodel.model.CommandContainer;
import net.draal.home.hs1xx.service.exception.Hs1xxException;

/**
 * Client for communication with the given device
 */
public interface DeviceClient {
    /**
     * Sends the given request to the given device and returns the response
     *
     * @param device The given device
     * @param request Request payload to be sent to the device
     * @return response from device
     * @throws Hs1xxException in case of any failure
     */
    CommandContainer send(Device device, CommandContainer request);
}
