package net.draal.home.hs1xx.service.converters

import net.draal.home.hs1xx.apimodel.model.CommandContainer
import net.draal.home.hs1xx.apimodel.model.SystemContainer
import net.draal.home.hs1xx.service.data.SystemInformation
import net.draal.home.hs1xx.service.impl.Hs1xxObjectMapperFactory
import spock.lang.Specification

import java.time.Duration
import java.time.temporal.ChronoUnit

class SystemInformationConverterTest extends Specification {
    SystemInformationConverter systemInformationConverter

    def setup() {
        systemInformationConverter = new SystemInformationConverter()
    }

    def 'if system is null in given DTO, exception is thrown'() {
        given:
        def givenData = CommandContainer.of((SystemContainer) null)

        when:
        systemInformationConverter.convert(givenData)

        then:
        thrown(IllegalArgumentException)
    }

    def 'if sysinfo is null in given DTO, exception is thrown'() {
        given:
        def givenData = CommandContainer.of((SystemContainer) null)

        when:
        systemInformationConverter.convert(givenData)

        then:
        thrown(IllegalArgumentException)
    }

    def 'all data is converted as expected'() {
        given:
        def givenData = filePayload

        when:
        def convert = systemInformationConverter.convert(givenData)

        then:
        convert == expectedResult
    }

    def getFilePayload() {
        def jsonUrl = this.class.classLoader.getResource('testdata/sysinfo-payload.json')
        Hs1xxObjectMapperFactory.getObjectMapper().readValue(jsonUrl, CommandContainer)
    }

    def getExpectedResult() {
        SystemInformation.builder()
                .alias('pmon1')
                .vendorProperties(vendorProperties)
                .location(location)
                .deviceState(deviceState)
                .networkInfo(networkInfo)
                .build()
    }

    def getVendorProperties() {
        SystemInformation.VendorProperties.builder()
                .firmwareVersion('1.0.4 Build 191111 Rel.143500')
                .hardwareVersion('4.0')
                .model('HS110(EU)')
                .deviceId('80065CB850F4A717CA096446BA35FC4A1CF45D69')
                .oemId('BC7DF59F436483CD3D8396111011B83E')
                .hardwareId('06D9793BF7C4C3E37A386CB6C5D4A929')
                .micType('IOT.SMARTPLUGSWITCH')
                .feature('TIM:ENE')
                .deviceName('Smart Wi-Fi Plug With Energy Monitoring')
                .build();
    }

    def getLocation() {
        SystemInformation.Location.builder()
                .longitude(21.637d)
                .latitude(58.1703d)
                .build();
    }

    def getDeviceState() {
        SystemInformation.DeviceState.builder()
                .status('new')
                .updating(false)
                .ledDisabled(false)
                .relayEnabled(true)
                .relayEnabledSince(Duration.of(3700L, ChronoUnit.SECONDS))
                .activeMode('none')
                .iconHash('')
                .build();
    }

    def getNetworkInfo() {
        SystemInformation.NetworkInfo.builder()
                .macAddress('B0:95:75:FC:CB:21')
                .rssi(-41)
                .build();
    }
}
