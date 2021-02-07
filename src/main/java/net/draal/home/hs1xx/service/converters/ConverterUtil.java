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
        Preconditions.checkArgument(energyDay.getYear() != null, "Year can not be null");
        int year = energyDay.getYear();
        int month = Objects.requireNonNullElse(energyDay.getMonth(), 1);
        int day = Objects.requireNonNullElse(energyDay.getDay(), 1);
        return TimePowerStats.builder()
                .energy(asDouble(energyDay.getEnergyWh()))
                .date(LocalDate.of(year, month, day))
                .build();
    }
}
