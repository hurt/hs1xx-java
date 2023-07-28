package net.draal.home.hs1xx.service.converters

import net.draal.home.hs1xx.apimodel.model.emeter.EnergyDay
import net.draal.home.hs1xx.service.data.TimePowerStats
import spock.lang.Specification
import spock.lang.Unroll

import java.time.DateTimeException
import java.time.Duration
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class ConverterUtilTest extends Specification {
    @Unroll
    def 'asScaledDouble(#givenValue, #givenExponent) returns #expectedResult'() {
        when:
        def actualResult = ConverterUtil.asScaledDouble(givenValue, givenExponent)

        then:
        actualResult == expectedResult

        where:
        givenValue | givenExponent || expectedResult
        12345      | 0             || 12345d
        12345      | -3            || 12.345d
        12345      | 3             || 12345000d
        null       | 0             || null
    }

    @Unroll
    def 'asDouble(#givenValue) returns #expectedResult'() {
        when:
        def actualResult = ConverterUtil.asDouble(givenValue)

        then:
        actualResult == expectedResult

        where:
        givenValue || expectedResult
        12345      || 12345d
        null       || null
    }

    @Unroll
    def 'asBoolean(#givenValue) returns #expectedResult'() {
        when:
        def actualResult = ConverterUtil.asBoolean(givenValue)

        then:
        actualResult == expectedResult

        where:
        givenValue || expectedResult
        0          || false
        1          || true
        null       || null
    }

    @Unroll
    def 'asBoolean(#givenValue) throws exception'() {
        when:
        ConverterUtil.asBoolean(givenValue)

        then:
        thrown(IllegalArgumentException)

        where:
        givenValue << [2, -1]
    }

    @Unroll
    def 'asDuration(#givenValue) returns #expectedResult'() {
        when:
        def actualResult = ConverterUtil.asDuration(givenValue)

        then:
        actualResult == expectedResult

        where:
        givenValue || expectedResult
        876        || Duration.of(876, ChronoUnit.SECONDS)
        -876       || Duration.of(-876, ChronoUnit.SECONDS)
        null       || null
    }

    @Unroll
    def 'asTimePowerStats(#givenValue) returns #expectedResult'() {
        when:
        def actualResult = ConverterUtil.asTimePowerStats(givenValue)

        then:
        actualResult == expectedResult

        where:
        givenValue                           || expectedResult
        getEnergyDay(2000, 10, 23, 123)      || getPowerStats(2000, 10, 23, 123d)
        getEnergyDay(2000, 10, 22, null)     || getPowerStats(2000, 10, 22, null)
        getEnergyDay(2000, 10, null, 2500)   || getPowerStats(2000, 10, 1, 2500d)
        getEnergyDay(2000, null, null, 2500) || getPowerStats(2000, 1, 1, 2500d)
        getEnergyDay(2000, null, 10, 2500)   || getPowerStats(2000, 1, 10, 2500d)
        null                                 || null
    }

    def 'asTimePowerStats throws exception if year is not given'() {
        given:
        def givenValue = getEnergyDay(null, 12, 13, 22)

        when:
        ConverterUtil.asTimePowerStats(givenValue)

        then:
        thrown(IllegalArgumentException)
    }

    def 'asTimePowerStats propagates DateTimeException if month=#givenMonth and day=#givenDay'() {
        given:
        def givenValue = getEnergyDay(2020, givenMonth, givenDay, 22)

        when:
        ConverterUtil.asTimePowerStats(givenValue)

        then:
        thrown(DateTimeException)

        where:
        givenMonth | givenDay
        13         | 20
        -1         | 20
        5          | -1
        4          | 32
    }

    static getEnergyDay(Integer year, Integer month, Integer day, Integer energy) {
        EnergyDay.builder()
                .year(year)
                .month(month)
                .day(day)
                .energyWh(energy)
                .build()
    }

    static getPowerStats(int year, int month, int day, Double energy) {
        TimePowerStats.builder()
                .date(LocalDate.of(year, month, day))
                .energy(energy)
                .build()
    }
}
