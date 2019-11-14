package com.hmtmcse.gauth.api

import com.hmtmcse.gauth.definition.InstanceIdentityDefinitionService
import com.hmtmcse.gs.GsRestProcessor

class ApiInstanceIdentityV1Controller extends GsRestProcessor {

    InstanceIdentityDefinitionService instanceIdentityDefinitionService

    def postRegistration() {
        return create(instanceIdentityDefinitionService.registration())
    }

    def postUpdate(){
        return update(instanceIdentityDefinitionService.update())
    }

    def deleteDelete(){
        return updateOnly(instanceIdentityDefinitionService.delete())
    }

    def deleteHardDelete(){
        return delete(instanceIdentityDefinitionService.hardDelete())
    }

    def postList(){
        return list(instanceIdentityDefinitionService.list())
    }

    def getList(){
        return list(instanceIdentityDefinitionService.list())
    }

    def postDetails(){
        return details(instanceIdentityDefinitionService.details())
    }

    def getDetails(){
        return details(instanceIdentityDefinitionService.details())
    }

    def getActiveInactive(){
        return customProcessor(instanceIdentityDefinitionService.activeInactive())
    }


    def getLogout(){}

    def postChangePassword(){}

    def postResetPassword(){}




}
