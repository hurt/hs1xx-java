package net.draal.home.hs1xx.apimodel

import spock.lang.Specification

class DeviceRequestTest extends Specification {
    private static final SOME_BYTES = 'a'.bytes
    private static final SOME_OTHER_BYTES = 'bla'.bytes

    def 'header or payload can not be null'() {
        when:
        DeviceRequest.builder().header(givenHeader).payload(givenPayload).build()

        then:
        thrown(IllegalArgumentException)

        where:
        givenHeader | givenPayload
        SOME_BYTES  | null
        null        | SOME_BYTES
        null        | null
    }

    def 'toString is logging-friendly'() {
        when:
        def actualString = DeviceRequest.builder().header(SOME_BYTES).payload(SOME_OTHER_BYTES).build().toString()

        then:
        actualString.contains('header=0x61')
        actualString.contains('payload=0x626c61')
        actualString.contains('reqlen=4')
    }
}
