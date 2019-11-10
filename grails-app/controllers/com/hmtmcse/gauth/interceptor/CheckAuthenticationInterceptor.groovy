package com.hmtmcse.gauth.interceptor

import com.hmtmcse.gauth.JwtAuthService
import com.hmtmcse.gauth.jwt.AuthenticationDataHolder
import com.hmtmcse.gs.data.GsApiResponseData
import grails.converters.JSON

class CheckAuthenticationInterceptor {

    JwtAuthService jwtAuthService

    CheckAuthenticationInterceptor() {
        matchAll()
    }

    private Boolean isSkipURL() {
        Map skipController = AuthenticationDataHolder.skipURL.get(controllerName.toString())
        if (skipController != null && skipController.isEmpty()) {
            return true
        } else if (skipController != null && skipController.get(actionName.toString())) {
            return true
        }
        return false
    }

    boolean before() {
        if (!AuthenticationDataHolder.isEnableJWTAuth || isSkipURL()) {
            return true
        }

        if (!jwtAuthService.isAuthenticated(request)) {
            response.status = 401
            render(GsApiResponseData.failed("Unauthorized Access").toMap() as JSON)
        } else {
            return true
        }

        return false
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
