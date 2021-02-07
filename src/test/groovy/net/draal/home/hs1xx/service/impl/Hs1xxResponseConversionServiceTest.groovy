package net.draal.home.hs1xx.service.impl

import net.draal.home.hs1xx.apimodel.model.CommandContainer
import net.draal.home.hs1xx.service.CommandResponseConverter
import net.draal.home.hs1xx.service.data.*
import net.draal.home.hs1xx.service.exception.Hs1xxException
import spock.lang.Specification
import spock.lang.Unroll

class Hs1xxResponseConversionServiceTest extends Specification {
    Hs1xxResponseConversionService responseConversionService

    CommandContainer commandContainer = Mock(CommandContainer)
    CommandResponseConverter dummyConverter = Mock(CommandResponseConverter)

    DummyObject dummyObject = new DummyObject()

    def setup() {
        responseConversionService = new Hs1xxResponseConversionService()
    }

    def 'calls appropriate converter'() {
        when:
        responseConversionService.registerConverter(DummyObject, dummyConverter)
        def actualTarget = responseConversionService.convert(commandContainer, DummyObject)

        then:
        1 * dummyConverter.convert(commandContainer) >> dummyObject
        actualTarget == dummyObject
    }

    def 'throw exception if no appropriate converter is found'() {
        when:
        responseConversionService.convert(commandContainer, DummyObject)

        then:
        thrown(Hs1xxException)
    }

    def 'register converter replaces existing converter'() {
        given:
        def givenTarget = Mock(DayPowerStats)
        responseConversionService.registerConverter(DayPowerStats, Mock(CommandResponseConverter))

        when:
        responseConversionService.registerConverter(DayPowerStats, dummyConverter)
        def actualTarget = responseConversionService.convert(commandContainer, DayPowerStats)

        then:
        1 * dummyConverter.convert(commandContainer) >> givenTarget
        actualTarget == givenTarget
    }

    def 'unregisterConverter removes existing converter'() {
        given:
        responseConversionService.registerConverter(DummyObject, dummyConverter)
        responseConversionService.unregisterConverter(DummyObject)

        when:
        responseConversionService.convert(commandContainer, DummyObject)

        then:
        thrown(Hs1xxException)
    }

    @Unroll
    def 'withDefaultConverters() configures converter for #expectedType'() {
        given:
        def defaultService = Hs1xxResponseConversionService.withDefaultConverters()

        when:
        def actualCanConvert = defaultService.canConvert(expectedType)

        then:
        actualCanConvert

        where:
        expectedType << [
                RealtimePowerStats,
                SystemInformation,
                DayPowerStats,
                MonthPowerStats,
                DeviceTime,
        ]
    }

    private class DummyObject {
    }
}
