package net.draal.home.hs1xx.service.impl

import net.draal.home.hs1xx.service.DeviceSocketFactory
import spock.lang.Specification

class DefaultDeviceSocketFactoryTest extends Specification {
    private static final TEST_PORT = 62134
    private static final SOCKET_TIMEOUT = 12_345
    DefaultDeviceSocketFactory deviceSocketFactory

    ServerSocket serverSocket

    def setup() {
        deviceSocketFactory = new DefaultDeviceSocketFactory()
        serverSocket = new ServerSocket(TEST_PORT)
    }

    def cleanup() {
        serverSocket.close()
    }

    def 'socket is created, connects and has correct socket timeout set'() {
        given:
        def givenDevice = Hs1xxDevice.builder()
                .host('localhost')
                .port(TEST_PORT)
                .socketTimeout(SOCKET_TIMEOUT)
                .build()

        when:
        def socket = deviceSocketFactory.createSocket(givenDevice)

        then:
        socket.connected
        socket.inetAddress.hostName == 'localhost'
        socket.port == TEST_PORT
        socket.getSoTimeout() == SOCKET_TIMEOUT
    }

    def 'given device is null, exception is thrown'() {
        when:
        deviceSocketFactory.createSocket(null)

        then:
        thrown(IllegalArgumentException)
    }

    def 'is default implementation'() {
        when:
        def actualDefault = DeviceSocketFactory.getDefault()

        then:
        actualDefault.class == DefaultDeviceSocketFactory
    }
}
