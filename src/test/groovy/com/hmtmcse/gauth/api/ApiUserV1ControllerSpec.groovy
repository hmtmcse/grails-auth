package com.hmtmcse.gauth.api

import com.hmtmcse.gauth.definition.UserDefinitionService
import com.hmtmcse.gs.GsRestfulService
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class ApiUserV1ControllerSpec extends Specification implements ControllerUnitTest<ApiUserV1Controller> {

    def setup() {
    }

    def cleanup() {
    }

    void "test postCreate Action"() {

        given:
        controller.userDefinitionService = Stub(UserDefinitionService)
        controller.gsRestfulService = Stub(GsRestfulService)


        when: "The message action is invoked"
        controller.postCreate()

        then: "Hello is returned"
        response.text == 'Hello'
    }
}
