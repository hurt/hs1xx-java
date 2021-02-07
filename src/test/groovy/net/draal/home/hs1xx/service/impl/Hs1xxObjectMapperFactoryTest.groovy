package net.draal.home.hs1xx.service.impl

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import spock.lang.Specification

class Hs1xxObjectMapperFactoryTest extends Specification {
    def 'object mapper is configured appropriately'() {
        when:
        def actualMapper = Hs1xxObjectMapperFactory.getObjectMapper()

        then:
        actualMapper.serializationConfig.serializationInclusion == JsonInclude.Include.NON_NULL
        !actualMapper.deserializationConfig.isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }
}
