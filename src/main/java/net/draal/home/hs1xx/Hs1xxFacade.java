package net.draal.home.hs1xx;

import lombok.extern.slf4j.Slf4j;
import net.draal.home.hs1xx.apimodel.model.CommandContainer;
import net.draal.home.hs1xx.service.*;
import net.draal.home.hs1xx.service.data.*;
import net.draal.home.hs1xx.service.impl.Hs1xxClient;
import net.draal.home.hs1xx.service.impl.Hs1xxObjectMapperFactory;
import net.draal.home.hs1xx.service.impl.Hs1xxRequestFactory;
import net.draal.home.hs1xx.service.impl.Hs1xxResponseConversionService;


@Slf4j
public class Hs1xxFacade {
    private final DeviceClient deviceClient;
    private final ResponseConversionService responseConversionService;

    public Hs1xxFacade(DeviceClient deviceClient, ResponseConversionService responseConversionService) {
        this.deviceClient = deviceClient;
        this.responseConversionService = responseConversionService;
    }

    public static Hs1xxFacade withDefaultConfiguration() {
        return new Hs1xxFacade(
                new Hs1xxClient(PayloadEncoder.getDefault(), Hs1xxObjectMapperFactory.getObjectMapper(), DeviceSocketFactory.getDefault()),
                Hs1xxResponseConversionService.withDefaultConverters());
    }

    public RealtimePowerStats getPowerStats(Device device) {
        CommandContainer realtimePowerStats = Hs1xxRequestFactory.getRealtimePowerStats();
        return sendInternal(device, realtimePowerStats, RealtimePowerStats.class);
    }

    public DayPowerStats getDailyPowerStats(Device device, int year, int month) {
        CommandContainer request = Hs1xxRequestFactory.getDailyPowerStats(year, month);
        return sendInternal(device, request, DayPowerStats.class);
    }

    public MonthPowerStats getMonthlyPowerStats(Device device, int year) {
        CommandContainer request = Hs1xxRequestFactory.getMonthlyPowerStats(year);
        return sendInternal(device, request, MonthPowerStats.class);
    }

    public SystemInformation getSystemInformation(Device device) {
        CommandContainer systemInfoRequest = Hs1xxRequestFactory.getSystemInfo();
        return sendInternal(device, systemInfoRequest, SystemInformation.class);
    }

    public DeviceTime getDeviceTime(Device device) {
        CommandContainer getTimeRequest = Hs1xxRequestFactory.getTime();
        return sendInternal(device, getTimeRequest, DeviceTime.class);
    }

    public void setRelayState(Device device, boolean state) {
        CommandContainer request = Hs1xxRequestFactory.setRelayState(state);
        sendInternal(device, request);
    }

    private void sendInternal(Device device, CommandContainer request) {
        deviceClient.send(device, request);
    }

    private <T> T sendInternal(Device device, CommandContainer request, Class<T> responseClass) {
        CommandContainer response = deviceClient.send(device, request);
        return responseConversionService.convert(response, responseClass);
    }
}
