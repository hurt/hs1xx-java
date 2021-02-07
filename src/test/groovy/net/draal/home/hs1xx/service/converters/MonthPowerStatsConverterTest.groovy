package net.draal.home.hs1xx.service.converters

import net.draal.home.hs1xx.apimodel.model.CommandContainer
import net.draal.home.hs1xx.apimodel.model.EmeterContainer
import net.draal.home.hs1xx.apimodel.model.emeter.EnergyDay
import net.draal.home.hs1xx.apimodel.model.emeter.MonthstatCommand
import net.draal.home.hs1xx.service.data.MonthPowerStats
import net.draal.home.hs1xx.service.data.TimePowerStats
import spock.lang.Specification

import java.time.LocalDate

class MonthPowerStatsConverterTest extends Specification {
    MonthPowerStatsConverter monthPowerStatsConverter

    def setup() {
        monthPowerStatsConverter = new MonthPowerStatsConverter()
    }

    def 'all data converted as expected'() {
        given:
        def givenData = new CommandContainer().setEmeter(
                new EmeterContainer().setMonthstatCommand(new MonthstatCommand().setMonthList(
                        List.of(
                                new EnergyDay().setYear(2020).setMonth(10).setEnergyWh(123),
                                new EnergyDay().setYear(2020).setMonth(11).setEnergyWh(223),
                                new EnergyDay().setYear(2020).setMonth(12).setEnergyWh(323),
                        ))))
        def expectedResult = MonthPowerStats.builder()
                .months(List.of(
                        TimePowerStats.builder().date(LocalDate.of(2020, 10, 1)).energy(123d).build(),
                        TimePowerStats.builder().date(LocalDate.of(2020, 11, 1)).energy(223d).build(),
                        TimePowerStats.builder().date(LocalDate.of(2020, 12, 1)).energy(323d).build()
                )).build()

        when:
        def actualResult = monthPowerStatsConverter.convert(givenData)

        then:
        actualResult == expectedResult
    }

    def 'null list in DTO is handled gracefully'() {
        given:
        def givenData = new CommandContainer().setEmeter(
                new EmeterContainer().setMonthstatCommand(new MonthstatCommand()))
        def expectedResult = MonthPowerStats.builder().months([]).build()

        when:
        def actualResult = monthPowerStatsConverter.convert(givenData)

        then:
        actualResult == expectedResult
    }

    def 'if emeter is null in given DTO, exception is thrown'() {
        given:
        def givenData = new CommandContainer()

        when:
        monthPowerStatsConverter.convert(givenData)

        then:
        thrown(IllegalArgumentException)
    }

    def 'if monthstat is null in given DTO, exception is thrown'() {
        given:
        def givenData = new CommandContainer().setEmeter(new EmeterContainer())

        when:
        monthPowerStatsConverter.convert(givenData)

        then:
        thrown(IllegalArgumentException)
    }

}
