package net.draal.home.hs1xx.service.impl

import spock.lang.Specification

class Hs1xxDeviceTest extends Specification {
    private static final HOST = '1.2.3.4'
    private static final EX_DEFAULT_PORT = 9999
    private static final EX_DEFAULT_TIMEOUT = 10_000i

    def 'uses default values for port and timeout'() {
        when:
        def actualDevice = Hs1xxDevice.builder().host(HOST).build()

        then:
        with(actualDevice) {
            verifyAll {
                host() == HOST
                port() == EX_DEFAULT_PORT
                socketTimeout() == EX_DEFAULT_TIMEOUT
            }
        }
    }

    def 'if host is null, exception is thrown'() {
        when:
        Hs1xxDevice.builder().build()

        then:
        thrown(IllegalArgumentException)
    }

    def 'uses provided values for port and timeout'() {
        given:
        def givenPort = 4567
        def givenTimeout = 20_000i

        when:
        def actualDevice = Hs1xxDevice.builder().host(HOST).port(givenPort).socketTimeout(givenTimeout).build()

        then:
        with(actualDevice) {
            verifyAll {
                host() == HOST
                port() == givenPort
                socketTimeout() == givenTimeout
            }
        }
    }
}
