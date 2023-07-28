package net.draal.home.hs1xx.service.impl;

import lombok.NonNull;
import net.draal.home.hs1xx.service.Device;
import net.draal.home.hs1xx.service.DeviceSocketFactory;

import java.io.IOException;
import java.net.Socket;

public class DefaultDeviceSocketFactory implements DeviceSocketFactory {
    @Override
    public Socket createSocket(@NonNull Device device) throws IOException {
        Socket socket = new Socket(device.host(), device.port());
        socket.setSoTimeout(device.socketTimeout());
        return socket;
    }
}
