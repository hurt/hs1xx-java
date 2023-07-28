package net.draal.home.hs1xx.service.converters

import net.draal.home.hs1xx.apimodel.model.CommandContainer
import net.draal.home.hs1xx.apimodel.model.EmeterContainer
import net.draal.home.hs1xx.apimodel.model.emeter.DaystatCommand
import net.draal.home.hs1xx.apimodel.model.emeter.EnergyDay
import net.draal.home.hs1xx.apimodel.model.emeter.MonthstatCommand
import net.draal.home.hs1xx.service.data.DayPowerStats
import net.draal.home.hs1xx.service.data.TimePowerStats
import spock.lang.Specification

import java.time.LocalDate

class DayPowerStatsConverterTest extends Specification {
    DayPowerStatsConverter dayPowerStatsConverter

    def setup() {
        dayPowerStatsConverter = new DayPowerStatsConverter()
    }

    def 'all data converted as expected'() {
        given:
        def givenData = CommandContainer.of(EmeterContainer.of(DaystatCommand.builder()
                .dayList([
                        EnergyDay.builder().year(2020).month(8).day(1).energyWh(458).build(),
                        EnergyDay.builder().year(2020).month(8).day(2).energyWh(358).build(),
                        EnergyDay.builder().year(2020).month(8).day(3).energyWh(258).build(),
                        EnergyDay.builder().year(2020).month(8).day(4).energyWh(158).build()
                ]).build()
        ))
        def expectedResult = DayPowerStats.builder()
                .days([
                        TimePowerStats.builder().date(LocalDate.of(2020, 8, 1)).energy(458).build(),
                        TimePowerStats.builder().date(LocalDate.of(2020, 8, 2)).energy(358).build(),
                        TimePowerStats.builder().date(LocalDate.of(2020, 8, 3)).energy(258).build(),
                        TimePowerStats.builder().date(LocalDate.of(2020, 8, 4)).energy(158).build()
                ]).build()

        when:
        def actualResult = dayPowerStatsConverter.convert(givenData)

        then:
        actualResult == expectedResult
    }

    def 'null list in DTO is handled gracefully'() {
        given:
        def givenData = CommandContainer.of(
                EmeterContainer.of(DaystatCommand.builder().build()))
        def expectedResult = DayPowerStats.builder().days([]).build()

        when:
        def actualResult = dayPowerStatsConverter.convert(givenData)

        then:
        actualResult == expectedResult
    }

    def 'if emeter is null in given DTO, exception is thrown'() {
        given:
        def givenData = CommandContainer.of((EmeterContainer) null)

        when:
        dayPowerStatsConverter.convert(givenData)

        then:
        thrown(IllegalArgumentException)
    }

    def 'if monthstat is null in given DTO, exception is thrown'() {
        given:
        def givenData = CommandContainer.of(EmeterContainer.of((MonthstatCommand) null))

        when:
        dayPowerStatsConverter.convert(givenData)

        then:
        thrown(IllegalArgumentException)
    }
}
