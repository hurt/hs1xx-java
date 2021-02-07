package net.draal.home.hs1xx.service.impl

import net.draal.home.hs1xx.apimodel.DeviceRequest
import net.draal.home.hs1xx.service.PayloadEncoder
import org.apache.commons.codec.binary.Hex
import spock.lang.Specification
import spock.lang.Unroll

class Hs1xxPayloadEncoderTest extends Specification {
    private static final VERY_LONG_STRING = 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc,'
    private static final VERY_LONG_PAYLOAD = 'e788fa9ff2d2bbcbb8cda080e48be788fadaa9c0b494f598fd89a585e689e794f192e683f782e795b5d4b0d9a9c0b3d0b9d7b090f599f084aa8acbaec0a5c4aa8ae986eb86e98de2c2aec7a0d5b9d8f89dfa9febcbafc0acc3b19fbffe9bf590f19fbfd2b3c0b3d2fcdc9fea87a7d4bbd8b1d8ab8be584f09fee9bfedeaecba5c4b0d9bbcebd9df88cacc1a0c7a9c0b393f79eedcdbddcaedaafddb4d1bfcbeb86e987f396e5c9e987e695f693e792e0c0b2dbbfd6b5c0acd9aa8ae792e1cfefabc4aacfac8cfd88e984a4c2a7cba2d1fddda8c4b0c2abc8a1c4b797f99cffd3f383e68ae683ed99fc8ffe8beeceabdef2d2a2d0b5c1a8ddb090e194fd8ea282f194f9d7f7b9cca0ccad8dee81ef9cf988fd9ce8c8a5c4b7c4a585f481e89bbbdeb0d9b49abafe91ff9af9d9a9cca8cded87f281f59ab696f082eb85e28be78beacabcd9b599b9d8b4ddacd9bcc8e886e380ac8cfa8fe393e692f387e2c2a7c0a5d1fdddbcceadd8f6d69ff1d1b4dab3defe94e192e689a585f79ff09efd88fbdbaedaf6d6bfd2a2c7b5d1b8dda989e8c4e492f799fc92f387ee9dbdcba2d6b7d2fedeb4c1b2c6a987a7e99cf09cfd90b0d4bddeaadfb292f491fd94e7c7a2d7f787e286e3c3aec1adc1a8dbfb8bf99ce881f499b797deb0c4a1c6a3d1f185ec82e188ec99f783ad8dcebcddae8eea8bfb92f085f6d8f8aec7b1d0bdc8bb9bfe92f79aff91e590fdddaecba6d6b3c1e18fe695fcd2f2b3d6b8ddbcd2f284f19ded98ec8df99cbcd9b5d0b9dfbad4b090e481ed81f487a989c8adc3a6c7a989e580efcfa3caadd8b4d5f9d9a9c6b4c0b4dda9c6b494f184a888eb84ea99fc8df899edcdbbd2a6c7a28eaecba7c2abcda8c6a282e380ac8ce987ee83ad8dcca0c9b8cdacc1e18de290f598b8d9b7c3a68aaaceafdfb6d4a1d2f29bf5d9f98fe690f587f594b4c5b0d9aa86a6c0a5d0b7debfcbeb8aa686f297fb97e291bf9fcfa7c6b5d0bcd0a5d6f680e99ffa88fa9bbbd5a0cca0c1e194e0c0adc8bcc9ba9aec8dff96e390b0dcbdd2a0c5a0d4fada8bfe97e495e085a5d7a2d6a4d1bc92b2f396f89dfc92b2dbb6c6a3d1b5dcb9cde3c386f29bfa97b7c2aedaa8c1a2cbaeddfd93fa89e0c0b6d3bf9ffe8bec99fcd2f2b1c4b6d7b5dca8ddaf8ffa96fa9bf695fa88f89defcfbad6a2d0b9dab3d6a585eb82f198b696d8b9d4f491f693e7c7a3d6bf91b1f480e988e5c5b7dfb0debdc8bb95b5f899fc9ffa94f586a6d2b7daaadfac80a0d4b1ddb1c4b797f295f084a4c7a8c6a2cba6c3add9acc1e193fb94fa99ec9fb393e085e8c8b9ccadc0e093f69beb8efcdcb0d9bbdeacc3efcfbcd5a181e08de89cbcddb9d0a0c9bad9b0deb999ea8fe2c2acc9b8cda888fb9efadab3c3b0c5a886a6e889e4c4b5c0a1ccec82f799fad6f694f899f793fa8eaed8bdd1fdddb1c4a7d3a6d5f585f09cea83ed8cfed2f29aff91f587e290f98dadc4a08cacc0afddb8d5fbdb96f792f194fa9be8c8a6c3a080ef8be28dadc8bc9cfd93e782a2d6bfd1b2dbbfcaa4d0f084e18cfc89fad4f4b0dfb1d4b797e188fc9df8d8abcabad3b6d8f88df9d9b5dcbedba9c6e690f59bfe90f185ec9fbfd9b8cdaec7a5d0a38dade396fa96f79abacbbed7a484e58bff9ab494d1a5ccadc0e093fa8eaecfa2c7b393fc8eed84a4c1a6c3b797f280ef9cbcdabbceadc4a6d3a080f49df390f99de886f2dcfcb8cda4d7f79bfe91bf9fcca9cded8bf990fe99f09cf091b1dcbdc8bad3a080f39aeeceafc2a7d3f39df496fed0f0b4dbb5d0b393e08feb8ae683f0d0a3c2a5ccb8cca5d6f69bfa9df392bc9ccfaaceee8de28cff9aeb9eff8ba787eb8ee1c1a4c3a6d2f290f99bfe90f481ecccbfd0b4d5b9dcaf83a3c2b7d0a5c0e096f39ff682a2c1b4c6b5c0b393fd88e685a9'
    private static final VERY_LONG_HEADER = '00000558'


    PayloadEncoder payloadConverter

    void setup() {
        payloadConverter = new Hs1xxPayloadEncoder()
    }

    def 'very long request is encoded'() {
        given:
        def expectedResult = DeviceRequest.builder()
                .payload(Hex.decodeHex(VERY_LONG_PAYLOAD))
                .header(Hex.decodeHex(VERY_LONG_HEADER))
                .build()

        when:
        def actualResult = payloadConverter.encodeRequest(VERY_LONG_STRING)

        then:
        actualResult == expectedResult
    }

    def 'very long header is decoded'() {
        when:
        def actualResult = payloadConverter.readHeader(Hex.decodeHex(VERY_LONG_HEADER))

        then:
        actualResult == VERY_LONG_STRING.length()
    }

    def 'very long payload is decoded'() {
        when:
        def actualResult = payloadConverter.decode(Hex.decodeHex(VERY_LONG_PAYLOAD))

        then:
        actualResult == VERY_LONG_STRING
    }

    def 'request is encoded properly'() {
        given:
        def expectedResult = DeviceRequest.builder()
                .header(Hex.decodeHex("0000000e"))
                .payload(Hex.decodeHex("e2c2a3ceee8fafc2a7d4a7c6a1c4"))
                .build()

        when:
        def actualResult = payloadConverter.encodeRequest("I am a message")

        then:
        actualResult == expectedResult
    }

    def 'header is read correctly'() {
        given:
        def givenHeader = Hex.decodeHex("0000000e")
        def expectedResult = 14

        when:
        def actualResult = payloadConverter.readHeader(givenHeader)

        then:
        actualResult == expectedResult
    }

    def 'data is decoded correctly'() {
        given:
        def givenMessage = Hex.decodeHex("e2c2a3ceee8fafc2a7d4a7c6a1c4")
        def expectedResult = 'I am a message'

        when:
        def actualResult = payloadConverter.decode(givenMessage)

        then:
        actualResult == expectedResult
    }

    @Unroll
    def 'header with incrrect length #givenHexHeader is given, exception is thrown'() {
        given:
        def givenHeader = Hex.decodeHex(givenHexHeader)

        when:
        payloadConverter.readHeader(givenHeader)

        then:
        thrown(IllegalArgumentException)

        where:
        givenHexHeader << ['aa0000000e', '00000e']
    }

    def 'is default implementation'() {
        when:
        def actualDefault = PayloadEncoder.getDefault()

        then:
        actualDefault.class == Hs1xxPayloadEncoder
    }
}
