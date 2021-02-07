package net.draal.home.hs1xx.service.impl;

import com.google.common.base.Preconditions;
import net.draal.home.hs1xx.service.Device;
import net.draal.home.hs1xx.service.DeviceSocketFactory;

import java.io.IOException;
import java.net.Socket;

public class DefaultDeviceSocketFactory implements DeviceSocketFactory {
    @Override
    public Socket createSocket(Device device) throws IOException {
        Preconditions.checkArgument(device != null, "Given device is null.");
        Socket socket = new Socket(device.getHost(), device.getPort());
        socket.setSoTimeout(device.getSocketTimeout());
        return socket;
    }
}
