package net.draal.home.hs1xx.service.converters

import net.draal.home.hs1xx.apimodel.model.CommandContainer
import net.draal.home.hs1xx.apimodel.model.TimeContainer
import net.draal.home.hs1xx.apimodel.model.time.GetTimeCommand
import net.draal.home.hs1xx.service.data.DeviceTime
import spock.lang.Specification

import java.time.LocalDateTime

class DeviceTimeConverterTest extends Specification {
    DeviceTimeConverter deviceTimeConverter

    def setup() {
        deviceTimeConverter = new DeviceTimeConverter()
    }

    def 'all data converted as expected'() {
        given:
        def givenData = CommandContainer.of(TimeContainer.of(GetTimeCommand.builder()
                .year(2021)
                .month(02)
                .mday(20)
                .hour(6)
                .min(56)
                .sec(41)
                .build()
        ))
        def expectedResult = DeviceTime.builder()
                .time(LocalDateTime.of(2021, 02, 20, 6, 56, 41))
                .build()

        when:
        def actualResult = deviceTimeConverter.convert(givenData)

        then:
        actualResult == expectedResult
    }

    def 'if time is null, exception is thrown'() {
        given:
        def givenData = CommandContainer.of((TimeContainer) null)

        when:
        deviceTimeConverter.convert(givenData)

        then:
        thrown(IllegalArgumentException)
    }

    def 'if getTimeCommand is null, exception is thrown'() {
        given:
        def givenData = CommandContainer.of(TimeContainer.of(null))

        when:
        deviceTimeConverter.convert(givenData)

        then:
        thrown(IllegalArgumentException)
    }
}
