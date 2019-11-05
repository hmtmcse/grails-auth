package com.hmtmcse.gauth.api

import com.hmtmcse.gauth.definition.UserDefinitionService
import com.hmtmcse.gs.GsRestProcessor

class ApiUserV1Controller extends GsRestProcessor {

    UserDefinitionService userDefinitionService

    def postCreate() {
        return create(userDefinitionService.create())
    }

    def postUpdate(){}

    def deleteDelete(){}

    def deleteHardDelete(){}

    def postList(){}

    def getList(){}

    def postDetails(){}

    def getDetails(){}


    def getLogout(){}

    def postChangePassword(){}

    def postResetPassword(){}

    def getActiveInactive(){}


}
