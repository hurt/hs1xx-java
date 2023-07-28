package net.draal.home.hs1xx.service.converters

import net.draal.home.hs1xx.apimodel.model.CommandContainer
import net.draal.home.hs1xx.apimodel.model.EmeterContainer
import net.draal.home.hs1xx.apimodel.model.emeter.RealtimeCommand
import net.draal.home.hs1xx.service.data.RealtimePowerStats
import spock.lang.Specification

class RealtimePowerStatsConverterTest extends Specification {
    RealtimePowerStatsConverter realtimePowerStatsConverter

    def setup() {
        realtimePowerStatsConverter = new RealtimePowerStatsConverter()
    }

    def 'all data converted as expected'() {
        given:
        def givenCommandContainer = CommandContainer.of(EmeterContainer.of(RealtimeCommand.builder()
                                .powerMw(25_000)
                                .currentMa(120)
                                .voltageMv(231_987)
                                .totalWh(548)
                                .build()
                        ))
        def expectedResult = RealtimePowerStats.builder()
                .power(25d)
                .current(0.12d)
                .voltage(231.987d)
                .totalEnergy(548d)
                .build()

        when:
        def actualResult = realtimePowerStatsConverter.convert(givenCommandContainer)

        then:
        actualResult == expectedResult
    }

    def 'all fields in dto are null, no exception is thrown'() {
        given:
        def givenCommandContainer = CommandContainer.of(EmeterContainer.of(RealtimeCommand.empty()))
        def expectedResult = RealtimePowerStats.builder().build()

        when:
        def actualResult = realtimePowerStatsConverter.convert(givenCommandContainer)

        then:
        actualResult == expectedResult
    }

    def 'given DTO does not have emeter attribute'() {
        given:
        def givenCommandContainer = CommandContainer.of((EmeterContainer) null)

        when:
        realtimePowerStatsConverter.convert(givenCommandContainer)

        then:
        thrown(IllegalArgumentException)
    }

    def 'given DTO does not have realtime attribute'() {
        given:
        def givenCommandContainer = CommandContainer.of(EmeterContainer.of((RealtimeCommand) null))

        when:
        realtimePowerStatsConverter.convert(givenCommandContainer)

        then:
        thrown(IllegalArgumentException)
    }
}
