package com.hmtmcse.gauth.definition

import com.hmtmcse.gauth.User
import com.hmtmcse.gauth.UserService
import com.hmtmcse.gs.GsApiActionDefinition
import com.hmtmcse.gs.data.ApiHelper
import com.hmtmcse.gs.data.GsApiResponseData
import com.hmtmcse.gs.data.GsParamsPairData
import com.hmtmcse.gs.model.CustomProcessor

class AuthenticationDefinitionService {

    UserService userService

    GsApiActionDefinition authDefinition() {
        GsApiActionDefinition gsApiActionDefinition = new GsApiActionDefinition<User>(User)
        gsApiActionDefinition.includeAllThenExcludeFromResponse(
                ["password", "dateCreated", "lastUpdated", "version", "isActive", "isDeleted"]
        )
        gsApiActionDefinition.successResponseAsData()
        return gsApiActionDefinition
    }

    GsApiActionDefinition login() {
        GsApiActionDefinition gsApiActionDefinition = authDefinition()
        gsApiActionDefinition.addRequestProperty("email").required()
        gsApiActionDefinition.addRequestProperty("password").required()
        gsApiActionDefinition.customProcessor = new CustomProcessor() {
            @Override
            GsApiResponseData process(GsApiActionDefinition actionDefinition, GsParamsPairData paramData, ApiHelper apiHelper) {
                return userService.login(actionDefinition, paramData, apiHelper)
            }
        }
        gsApiActionDefinition.failedResponseFormat = GsApiResponseData.failed("Invalid email or password !!")
        return gsApiActionDefinition
    }


    GsApiActionDefinition logout() {
        GsApiActionDefinition gsApiActionDefinition = new GsApiActionDefinition<User>(User)
        gsApiActionDefinition.customProcessor = new CustomProcessor() {
            @Override
            GsApiResponseData process(GsApiActionDefinition actionDefinition, GsParamsPairData paramData, ApiHelper apiHelper) {
                return apiHelper.help.responseMessageToApi(actionDefinition, true)
            }
        }
        gsApiActionDefinition.successResponseFormat = GsApiResponseData.successMessage("Logout Success!!")
        gsApiActionDefinition.failedResponseFormat = GsApiResponseData.failed("Logout Failed!!")
        return gsApiActionDefinition
    }

    GsApiActionDefinition renew() {
        GsApiActionDefinition gsApiActionDefinition = authDefinition()
        gsApiActionDefinition.customProcessor = new CustomProcessor() {
            @Override
            GsApiResponseData process(GsApiActionDefinition actionDefinition, GsParamsPairData paramData, ApiHelper apiHelper) {
                return userService.renew(actionDefinition, paramData, apiHelper)
            }
        }
        gsApiActionDefinition.addRequestProperty("refreshToken").required()
        gsApiActionDefinition.failedResponseFormat = GsApiResponseData.failed("Token Expired!")
        return gsApiActionDefinition
    }


}
