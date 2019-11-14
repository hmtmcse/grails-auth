package com.hmtmcse.gauth.definition


import com.hmtmcse.gauth.InstanceIdentityService
import com.hmtmcse.gs.GsApiActionDefinition
import com.hmtmcse.gs.GsUtil
import com.hmtmcse.gs.data.ApiHelper
import com.hmtmcse.gs.data.GsApiResponseData
import com.hmtmcse.gs.data.GsFilteredData
import com.hmtmcse.gs.data.GsParamsPairData
import com.hmtmcse.gs.model.CustomProcessor
import com.hmtmcse.gs.model.RequestPreProcessor
import com.hmtmcse.org.InstanceIdentity

class InstanceIdentityDefinitionService {

    InstanceIdentityService instanceIdentityService


    private GsApiActionDefinition createUpdate() {
        GsApiActionDefinition gsApiActionDefinition = new GsApiActionDefinition<InstanceIdentity>(InstanceIdentity)
        gsApiActionDefinition.addRequestProperty("name").required().setErrorMessage("Please Enter Name.")
        gsApiActionDefinition.addRequestProperty("ownerName")
        gsApiActionDefinition.addRequestProperty("ownerMobile")
        gsApiActionDefinition.addRequestProperty("ownerPhone")
        gsApiActionDefinition.addRequestProperty("address")
        gsApiActionDefinition.addRequestProperty("ownerEmail").setErrorMessage("Please Enter Email Address.").required()
        gsApiActionDefinition.addResponseProperty("uuid")
        gsApiActionDefinition.addResponseProperty("id")
        return gsApiActionDefinition
    }


    private GsApiActionDefinition read() {
        GsApiActionDefinition gsApiActionDefinition = new GsApiActionDefinition<InstanceIdentity>(InstanceIdentity)
        gsApiActionDefinition.includeAllNotRelationalThenExcludeFromResponse(["password", "version"])
        gsApiActionDefinition = GsUtil.commonHelpService.responseDateFormat(gsApiActionDefinition, "dateCreated")
        gsApiActionDefinition = GsUtil.commonHelpService.responseDateFormat(gsApiActionDefinition, "lastUpdated")
        gsApiActionDefinition.requestPreProcessor = new RequestPreProcessor() {
            @Override
            GsFilteredData process(GsApiActionDefinition definition, GsFilteredData gsFilteredData) {
                definition.addToWhereFilterProperty("isDeleted")
                gsFilteredData.where.addEqual("isDeleted", false)
                return gsFilteredData
            }
        }
        return gsApiActionDefinition
    }


    private GsApiActionDefinition allowedInWhere(GsApiActionDefinition gsApiActionDefinition) {
        gsApiActionDefinition.addToWhereFilterProperty("id")
        gsApiActionDefinition.addToWhereFilterProperty("uuid")
        gsApiActionDefinition.includeAllThenExcludeFromWhereFilter(["password", "message", "isDeleted", "profilePicture"])
        return gsApiActionDefinition
    }


    GsApiActionDefinition registration() {
        GsApiActionDefinition gsApiActionDefinition = createUpdate()

        gsApiActionDefinition.customProcessor = new CustomProcessor() {
            @Override
            GsApiResponseData process(GsApiActionDefinition actionDefinition, GsParamsPairData paramData, ApiHelper apiHelper) {
                return  instanceIdentityService.registration(actionDefinition, paramData, apiHelper)
            }
        }

        gsApiActionDefinition.addRequestProperty("password").required().setErrorMessage("Please Enter Password.")
        return gsApiActionDefinition
    }


    GsApiActionDefinition update() {
        GsApiActionDefinition gsApiActionDefinition = createUpdate()
        gsApiActionDefinition.includeInWhereFilter(["id", "uuid"])
        return gsApiActionDefinition
    }


    GsApiActionDefinition delete() {
        GsApiActionDefinition gsApiActionDefinition = new GsApiActionDefinition<InstanceIdentity>(InstanceIdentity)
        gsApiActionDefinition.addToWhereFilterProperty("id").enableTypeCast()
        gsApiActionDefinition.addToWhereFilterProperty("uuid")
        gsApiActionDefinition.successResponseFormat = GsApiResponseData.successMessage("Successfully Deleted")
        gsApiActionDefinition.failedResponseFormat = GsApiResponseData.failed("Unable to Delete")
        gsApiActionDefinition.requestPreProcessor = new RequestPreProcessor() {
            @Override
            GsFilteredData process(GsApiActionDefinition definition, GsFilteredData gsFilteredData) {
                gsFilteredData.gsParamsPairData.addToParams("isDeleted", true)
                return gsFilteredData
            }
        }
        gsApiActionDefinition.allowedConditionOnlyEqual()
        gsApiActionDefinition.disablePaginationAndSorting()
        gsApiActionDefinition.enableQueryFilter()
        return gsApiActionDefinition
    }

    GsApiActionDefinition hardDelete() {
        GsApiActionDefinition gsApiActionDefinition = new GsApiActionDefinition<InstanceIdentity>(InstanceIdentity)
        gsApiActionDefinition.includeInWhereFilter(["id", "uuid"])
        return gsApiActionDefinition
    }

    GsApiActionDefinition list() {
        return allowedInWhere(read())
    }

    GsApiActionDefinition details() {
        return allowedInWhere(read())
    }

    GsApiActionDefinition resetPassword(){
        GsApiActionDefinition gsApiActionDefinition = new GsApiActionDefinition<InstanceIdentity>(InstanceIdentity)
        gsApiActionDefinition.addRequestProperty("id").required().enableTypeCast()
        gsApiActionDefinition.addRequestProperty("password").required()
        gsApiActionDefinition.addRequestProperty("confirmPassword").required()
        gsApiActionDefinition.customProcessor = new CustomProcessor() {
            @Override
            GsApiResponseData process(GsApiActionDefinition actionDefinition, GsParamsPairData paramData, ApiHelper apiHelper) {
                return instanceIdentityService.restPassword(actionDefinition, paramData, apiHelper)
            }
        }
        gsApiActionDefinition.successResponseFormat = GsApiResponseData.successMessage("Successfully Password Reset.")
        gsApiActionDefinition.successResponseAsData()
        return gsApiActionDefinition
    }


    GsApiActionDefinition activeInactive(){
        GsApiActionDefinition gsApiActionDefinition = new GsApiActionDefinition<InstanceIdentity>(InstanceIdentity)
        gsApiActionDefinition.customProcessor = new CustomProcessor() {
            @Override
            GsApiResponseData process(GsApiActionDefinition actionDefinition, GsParamsPairData paramData, ApiHelper apiHelper) {
                return  instanceIdentityService.activeInactive(actionDefinition, paramData, apiHelper)
            }
        }
        gsApiActionDefinition.successResponseFormat = GsApiResponseData.successMessage("Operation Success")
        gsApiActionDefinition.successResponseAsData()
        gsApiActionDefinition.addRequestProperty("id")
        gsApiActionDefinition.addRequestProperty("uuid")
        return gsApiActionDefinition
    }


}
