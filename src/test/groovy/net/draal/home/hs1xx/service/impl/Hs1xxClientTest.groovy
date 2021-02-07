package net.draal.home.hs1xx.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import net.draal.home.hs1xx.apimodel.DeviceRequest
import net.draal.home.hs1xx.apimodel.model.CommandContainer
import net.draal.home.hs1xx.service.Device
import net.draal.home.hs1xx.service.DeviceSocketFactory
import net.draal.home.hs1xx.service.PayloadEncoder
import net.draal.home.hs1xx.service.exception.Hs1xxException
import spock.lang.Specification

class Hs1xxClientTest extends Specification {
    private static final REQUEST_PAYLOAD_STRING = 'Request payload'
    private static final REQUEST_PAYLOAD_BYTES = REQUEST_PAYLOAD_STRING.bytes
    private static final REQUEST_PAYLOAD_HEADER = '1234'.bytes

    private static final RESPONSE_PAYLOAD_STRING = 'Response payload'
    private static final RESPONSE_PAYLOAD_BYTES = REQUEST_PAYLOAD_STRING.bytes
    private static final RESPONSE_PAYLOAD_HEADER = '4321'.bytes
    private static final RESPONSE_LENGTH = 4321


    PayloadEncoder payloadEncoder = Mock(PayloadEncoder)
    ObjectMapper objectMapper = Mock(ObjectMapper)
    DeviceSocketFactory deviceSocketFactory = Mock(DeviceSocketFactory)

    Hs1xxClient client

    Device mockDevice = Mock(Device)
    CommandContainer mockRequest = Mock(CommandContainer)
    CommandContainer mockResponse = Mock(CommandContainer)
    Socket mockSocket = Mock(Socket)
    OutputStream mockOutputStream = Mock(OutputStream)
    InputStream mockInputStream = Mock(InputStream)

    def setup() {
        client = new Hs1xxClient(payloadEncoder, objectMapper, deviceSocketFactory)

        objectMapper.writeValueAsString(mockRequest) >> REQUEST_PAYLOAD_STRING
        payloadEncoder.encodeRequest(REQUEST_PAYLOAD_STRING) >> DeviceRequest.builder()
                .header(REQUEST_PAYLOAD_HEADER)
                .payload(REQUEST_PAYLOAD_BYTES)
                .build()
        payloadEncoder.readHeader(RESPONSE_PAYLOAD_HEADER) >> RESPONSE_LENGTH
        payloadEncoder.decode(RESPONSE_PAYLOAD_BYTES) >> RESPONSE_PAYLOAD_STRING
        objectMapper.readValue(RESPONSE_PAYLOAD_STRING, CommandContainer) >> mockResponse

        deviceSocketFactory.createSocket(mockDevice) >> mockSocket
        mockSocket.getOutputStream() >> mockOutputStream
        mockSocket.getInputStream() >> mockInputStream
        mockInputStream.readNBytes(4) >> RESPONSE_PAYLOAD_HEADER
        mockInputStream.readNBytes(RESPONSE_LENGTH) >> RESPONSE_PAYLOAD_BYTES
    }

    def 'sent request writes appropriate data to socket and returns response'() {
        when:
        def actualResponse = client.send(mockDevice, mockRequest)

        then:
        1 * mockOutputStream.write(REQUEST_PAYLOAD_HEADER)
        1 * mockOutputStream.write(REQUEST_PAYLOAD_BYTES)
        actualResponse == mockResponse
    }

    def 'exception thrown in socket factory is wrapped'() {
        given:
        def givenException = new IOException()

        when:
        client.send(mockDevice, mockRequest)

        then:
        deviceSocketFactory.createSocket(mockDevice) >> { throw givenException }
        def thrownException = thrown(Hs1xxException)
        thrownException.cause == givenException
    }

    def 'exception thrown in payload encoder is wrapped and connection is not attempted'() {
        given:
        def givenException = new RuntimeException()

        when:
        client.send(mockDevice, mockRequest)

        then:
        payloadEncoder.encodeRequest(_ as String) >> { throw givenException }
        def thrownException = thrown(Hs1xxException)
        thrownException.cause == givenException
        0 * deviceSocketFactory._
    }

    def 'exception thrown when mapping request is wrapped and connection is not attempted'() {
        given:
        def givenException = new RuntimeException()

        when:
        client.send(mockDevice, mockRequest)

        then:
        objectMapper.writeValueAsString(_ as CommandContainer) >> { throw givenException }
        def thrownException = thrown(Hs1xxException)
        thrownException.cause == givenException
        0 * deviceSocketFactory._
    }

    def 'exception thrown while reading header is wrapped and connection is closed'() {
        given:
        def givenException = new RuntimeException()

        when:
        client.send(mockDevice, mockRequest)

        then:
        payloadEncoder.readHeader(_ as byte[]) >> { throw givenException }
        def thrownException = thrown(Hs1xxException)
        thrownException.cause == givenException
        1 * mockSocket.close()
    }

    def 'exception thrown while converting message is wrapped and connection is closed'() {
        given:
        def givenException = new RuntimeException()

        when:
        client.send(mockDevice, mockRequest)

        then:
        payloadEncoder.decode(_ as byte[]) >> { throw givenException }
        def thrownException = thrown(Hs1xxException)
        thrownException.cause == givenException
        1 * mockSocket.close()
    }

    def 'exception thrown while mapping response is wrapped and connection is closed'() {
        given:
        def givenException = new RuntimeException()

        when:
        client.send(mockDevice, mockRequest)

        then:
        objectMapper.readValue(_ as String, CommandContainer) >> { throw givenException }
        def thrownException = thrown(Hs1xxException)
        thrownException.cause == givenException
        1 * mockSocket.close()
    }

    def 'exception thrown while reading response is wrapped and connection is closed'() {
        given:
        def givenException = new IOException()

        when:
        client.send(mockDevice, mockRequest)

        then:
        mockInputStream.readNBytes(_ as Integer) >> { throw givenException }
        def thrownException = thrown(Hs1xxException)
        thrownException.cause == givenException
        1 * mockSocket.close()
    }

}
