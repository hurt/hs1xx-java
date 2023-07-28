package net.draal.home.hs1xx.service.impl;

import net.draal.home.hs1xx.apimodel.model.CommandContainer;
import net.draal.home.hs1xx.apimodel.model.EmeterContainer;
import net.draal.home.hs1xx.apimodel.model.SystemContainer;
import net.draal.home.hs1xx.apimodel.model.TimeContainer;
import net.draal.home.hs1xx.apimodel.model.emeter.DaystatCommand;
import net.draal.home.hs1xx.apimodel.model.emeter.MonthstatCommand;
import net.draal.home.hs1xx.apimodel.model.emeter.RealtimeCommand;
import net.draal.home.hs1xx.apimodel.model.system.LedStateCommand;
import net.draal.home.hs1xx.apimodel.model.system.RelayStateCommand;
import net.draal.home.hs1xx.apimodel.model.system.SysinfoCommand;
import net.draal.home.hs1xx.apimodel.model.time.GetTimeCommand;

public class Hs1xxRequestFactory {
    private Hs1xxRequestFactory() {
        // noop
    }

    public static CommandContainer getRealtimePowerStats() {
        return CommandContainer.of(EmeterContainer.of(RealtimeCommand.empty()));
    }

    public static CommandContainer getSystemInfo() {
        return CommandContainer.of(SystemContainer.of(SysinfoCommand.empty()));
    }

    public static CommandContainer setRelayState(boolean state) {
        return CommandContainer.of(SystemContainer.of(RelayStateCommand.ofState(state ? 1 : 0)));
    }

    public static CommandContainer setLedState(boolean state) {
        return CommandContainer.of(SystemContainer.of(LedStateCommand.builder().off(state ? 0 : 1).build()));
    }

    public static CommandContainer getDailyPowerStats(int year, int month) {
        return CommandContainer.of(EmeterContainer.of(DaystatCommand.builder().year(year).month(month).build()));
    }

    public static CommandContainer getMonthlyPowerStats(int year) {
        return CommandContainer.of(EmeterContainer.of(MonthstatCommand.ofYear(year)));
    }

    public static CommandContainer getTime() {
        return CommandContainer.of(TimeContainer.of(GetTimeCommand.empty()));
    }
}
