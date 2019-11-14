package com.hmtmcse.gauth.interceptor

import grails.testing.web.interceptor.InterceptorUnitTest
import spock.lang.Specification

class CheckAuthenticationInterceptorSpec extends Specification implements InterceptorUnitTest<CheckAuthenticationInterceptor> {

    def setup() {
    }

    def cleanup() {

    }

    void "Test checkAuthentication interceptor matching"() {
        when:"A request matches the interceptor"
            withRequest(controller:"checkAuthentication")

        then:"The interceptor does match"
            interceptor.doesMatch()
    }
}
