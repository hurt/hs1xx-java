package net.draal.home.hs1xx

import net.draal.home.hs1xx.service.Device
import net.draal.home.hs1xx.service.exception.Hs1xxException
import net.draal.home.hs1xx.service.impl.Hs1xxDevice
import net.draal.home.hs1xx.testutil.SimpleFlow
import spock.lang.Specification
import us.abstracta.wiresham.VirtualTcpService

import java.time.LocalDate
import java.time.LocalDateTime

class Hs1xxFacadeTest extends Specification {
    private static final MOCK_PORT = 61321

    Hs1xxFacade hs1xxFacade
    Device mockDevice
    VirtualTcpService service = new VirtualTcpService()

    def setup() {
        hs1xxFacade = Hs1xxFacade.withDefaultConfiguration()
        mockDevice = Hs1xxDevice.builder()
                .host('localhost')
                .port(MOCK_PORT)
                .socketTimeout(1000)
                .build()
        service.setPort(MOCK_PORT)
        service.start()
    }

    def cleanup() {
        service.stop(1000L)
    }


    def 'get power stats'() {
        given:
        service.setFlow(SimpleFlow.getFlow(getResourceUri('testdata/powerstats.yaml')))

        when:
        def stats = hs1xxFacade.getPowerStats(mockDevice)

        then:
        with(stats) {
            verifyAll {
                voltage == 232.696d
                current == 0.029
                power == 0d
                totalEnergy == 1d
            }
        }
    }

    def 'set relay on'() {
        given:
        service.setFlow(SimpleFlow.getFlow(getResourceUri('testdata/relayon.yaml')))

        when:
        hs1xxFacade.setRelayState(mockDevice, true)

        then:
        true
    }

    def 'set relay off'() {
        given:
        service.setFlow(SimpleFlow.getFlow(getResourceUri('testdata/relayoff.yaml')))

        when:
        hs1xxFacade.setRelayState(mockDevice, false)

        then:
        true
    }

    def 'set LED on'() {
        given:
        service.setFlow(SimpleFlow.getFlow(getResourceUri('testdata/ledon.yaml')))

        when:
        hs1xxFacade.setLedState(mockDevice, true)

        then:
        true
    }

    def 'set LED off'() {
        given:
        service.setFlow(SimpleFlow.getFlow(getResourceUri('testdata/ledoff.yaml')))

        when:
        hs1xxFacade.setLedState(mockDevice, false)

        then:
        true
    }

    def 'get system info'() {
        given:
        service.setFlow(SimpleFlow.getFlow(getResourceUri('testdata/sysinfo.yaml')))

        when:
        def state = hs1xxFacade.getSystemInformation(mockDevice)

        then:
        with(state) {
            verifyAll {
                alias == 'pmon1'
            }
        }
    }

    def 'get day stats'() {
        given:
        service.setFlow(SimpleFlow.getFlow(getResourceUri('testdata/daystat.yaml')))

        when:
        def stats = hs1xxFacade.getDailyPowerStats(mockDevice, 2021, 2)

        then:
        with(stats) {
            verifyAll {
                days.size() == 2
                days.find { it.date == LocalDate.of(2021, 2, 6) }.energy == 21
                days.find { it.date == LocalDate.of(2021, 2, 7) }.energy == 25
            }
        }
    }

    def 'get month stats'() {
        given:
        service.setFlow(SimpleFlow.getFlow(getResourceUri('testdata/monthstat.yaml')))

        when:
        def stats = hs1xxFacade.getMonthlyPowerStats(mockDevice, 2020)

        then:
        with(stats) {
            verifyAll {
                months.size() == 1
                months.find { it.date == LocalDate.of(2020, 11, 1) }.energy == 7
            }
        }
    }

    def 'get device time'() {
        given:
        service.setFlow(SimpleFlow.getFlow(getResourceUri('testdata/time.yaml')))

        when:
        def deviceTime = hs1xxFacade.getDeviceTime(mockDevice)

        then:
        deviceTime.asLocalDateTime() == LocalDateTime.of(2021, 2, 20, 6, 34, 41)
    }

    def 'thrown exception is handled'() {
        given:
        service.stop(10L)

        when:
        hs1xxFacade.getMonthlyPowerStats(mockDevice, 2020)

        then:
        thrown(Hs1xxException)
    }

    def getResourceUri(String filename) {
        this.class.classLoader.getResource(filename).toURI()
    }
}
