package net.draal.home.hs1xx.service.converters;

import com.google.common.base.Preconditions;
import net.draal.home.hs1xx.apimodel.model.emeter.EnergyDay;
import net.draal.home.hs1xx.service.data.TimePowerStats;

import javax.annotation.CheckForNull;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class ConverterUtil {
    private ConverterUtil() {
        // noop
    }

    public static Duration asDuration(Integer seconds) {
        if (seconds == null) {
            return null;
        }
        return Duration.of(seconds, ChronoUnit.SECONDS);
    }

    @CheckForNull
    public static Boolean asBoolean(Integer value) {
        if (value == null) {
            return null;
        } else if (Integer.valueOf(1).equals(value)) {
            return Boolean.TRUE;
        } else if (Integer.valueOf(0).equals(value)) {
            return Boolean.FALSE;
        } else {
            throw new IllegalArgumentException("Got invalid value for boolean");
        }
    }

    public static Double asDouble(Integer value) {
        return asScaledDouble(value, 0);
    }

    public static Double asScaledDouble(Integer value, int factorExponent) {
        if (value == null) {
            return null;
        }
        return BigDecimal.valueOf(value).movePointRight(factorExponent).doubleValue();
    }

    public static TimePowerStats asTimePowerStats(EnergyDay energyDay) {
        if (energyDay == null) {
            return null;
        }
        Preconditions.checkArgument(energyDay.year() != null, "Year can not be null");
        int year = energyDay.year();
        int month = Objects.requireNonNullElse(energyDay.month(), 1);
        int day = Objects.requireNonNullElse(energyDay.day(), 1);
        return TimePowerStats.builder()
                .energy(asDouble(energyDay.energyWh()))
                .date(LocalDate.of(year, month, day))
                .build();
    }
}
