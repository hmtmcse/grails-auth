package com.hmtmcse.gauth.api

import com.hmtmcse.gauth.definition.UserDefinitionService
import com.hmtmcse.gs.GsRestProcessor

class ApiUserV1Controller extends GsRestProcessor {

    UserDefinitionService userDefinitionService

    def postCreate() {
        return create(userDefinitionService.create())
    }

    def postUpdate(){
        return update(userDefinitionService.update())
    }

    def deleteDelete(){
        return updateOnly(userDefinitionService.delete())
    }

    def deleteHardDelete(){
        return delete(userDefinitionService.hardDelete())
    }

    def postList(){
        return list(userDefinitionService.list())
    }

    def getList(){
        return list(userDefinitionService.list())
    }

    def postDetails(){
        return details(userDefinitionService.details())
    }

    def getDetails(){
        return details(userDefinitionService.details())
    }

    def getActiveInactive(){
        return customProcessor(userDefinitionService.activeInactive())
    }




    def postResetPassword(){}




}
