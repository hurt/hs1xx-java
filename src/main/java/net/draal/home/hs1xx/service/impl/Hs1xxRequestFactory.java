package net.draal.home.hs1xx.service.impl;

import net.draal.home.hs1xx.apimodel.model.CommandContainer;
import net.draal.home.hs1xx.apimodel.model.EmeterContainer;
import net.draal.home.hs1xx.apimodel.model.SystemContainer;
import net.draal.home.hs1xx.apimodel.model.TimeContainer;
import net.draal.home.hs1xx.apimodel.model.emeter.DaystatCommand;
import net.draal.home.hs1xx.apimodel.model.emeter.MonthstatCommand;
import net.draal.home.hs1xx.apimodel.model.emeter.RealtimeCommand;
import net.draal.home.hs1xx.apimodel.model.system.RelayStateCommand;
import net.draal.home.hs1xx.apimodel.model.system.SysinfoCommand;
import net.draal.home.hs1xx.apimodel.model.time.GetTimeCommand;

public class Hs1xxRequestFactory {
    private Hs1xxRequestFactory() {
        // noop
    }

    public static CommandContainer getRealtimePowerStats() {
        return new CommandContainer().setEmeter(new EmeterContainer().setRealtimeCommand(new RealtimeCommand()));
    }

    public static CommandContainer getSystemInfo() {
        return new CommandContainer().setSystem(new SystemContainer().setSysinfoCommand(new SysinfoCommand()));
    }

    public static CommandContainer setRelayState(boolean state) {
        return new CommandContainer().setSystem(new SystemContainer().setRelayStateCommand(new RelayStateCommand().setState(state ? 1 : 0)));
    }

    public static CommandContainer getDailyPowerStats(int year, int month) {
        return new CommandContainer().setEmeter(new EmeterContainer().setDaystatCommand(DaystatCommand.builder().year(year).month(month).build()));
    }

    public static CommandContainer getMonthlyPowerStats(int year) {
        return new CommandContainer().setEmeter(new EmeterContainer().setMonthstatCommand(new MonthstatCommand().setYear(year)));
    }

    public static CommandContainer getTime() {
        return new CommandContainer().setTime(new TimeContainer().setGetTimeCommand(new GetTimeCommand()));
    }
}
