package com.hmtmcse.gauth.definition

import com.hmtmcse.gauth.User
import com.hmtmcse.gauth.UserService
import com.hmtmcse.gs.GsApiActionDefinition
import com.hmtmcse.gs.GsRequestParamException
import com.hmtmcse.gs.GsUtil
import com.hmtmcse.gs.data.ApiHelper
import com.hmtmcse.gs.data.GsApiRequestProperty
import com.hmtmcse.gs.data.GsApiResponseData
import com.hmtmcse.gs.data.GsFilteredData
import com.hmtmcse.gs.data.GsParamsPairData
import com.hmtmcse.gs.model.CustomProcessor
import com.hmtmcse.gs.model.CustomRequestParamProcessor
import com.hmtmcse.gs.model.RequestPreProcessor


class UserDefinitionService {

    UserService userService


    private GsApiActionDefinition createUpdate() {
        GsApiActionDefinition gsApiActionDefinition = new GsApiActionDefinition<User>(User)
        gsApiActionDefinition.addRequestProperty("firstName").required().setErrorMessage("Please Enter First Name.")
        gsApiActionDefinition.addRequestProperty("lastName")
        gsApiActionDefinition.addRequestProperty("email").setErrorMessage("Please Enter Email Address.")
                .required()
//                .customRequestParamProcessor = new CustomRequestParamProcessor() {
//            @Override
//            Object process(String fieldName, GsParamsPairData gsParamsPairData, GsApiRequestProperty propertyDefinition) throws GsRequestParamException {
//                String email = gsParamsPairData.params.email
//                if (userService.isEmailExist(email)) {
//                    throw new GsRequestParamException(email + " Email already exists. Please try with other Email.")
//                }
//                return email
//            }
//        }
        gsApiActionDefinition.addResponseProperty("uuid")
        gsApiActionDefinition.addResponseProperty("id")
        return gsApiActionDefinition
    }


    private GsApiActionDefinition read() {
        GsApiActionDefinition gsApiActionDefinition = new GsApiActionDefinition<User>(User)
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


    GsApiActionDefinition create() {
        GsApiActionDefinition gsApiActionDefinition = createUpdate()
        gsApiActionDefinition.addRequestProperty("password").required().setErrorMessage("Please Enter Password.")
        return gsApiActionDefinition
    }


    GsApiActionDefinition update() {
        GsApiActionDefinition gsApiActionDefinition = createUpdate()
        gsApiActionDefinition.includeInWhereFilter(["id", "uuid"])
        return gsApiActionDefinition
    }


    GsApiActionDefinition delete() {
        GsApiActionDefinition gsApiActionDefinition = new GsApiActionDefinition<User>(User)
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
        GsApiActionDefinition gsApiActionDefinition = new GsApiActionDefinition<User>(User)
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
        GsApiActionDefinition gsApiActionDefinition = new GsApiActionDefinition<User>(User)
        gsApiActionDefinition.addRequestProperty("id").required().enableTypeCast()
        gsApiActionDefinition.addRequestProperty("password").required()
        gsApiActionDefinition.addRequestProperty("confirmPassword").required()
        gsApiActionDefinition.customProcessor = new CustomProcessor() {
            @Override
            GsApiResponseData process(GsApiActionDefinition actionDefinition, GsParamsPairData paramData, ApiHelper apiHelper) {
                return userService.restPassword(actionDefinition, paramData, apiHelper)
            }
        }
        gsApiActionDefinition.successResponseFormat = GsApiResponseData.successMessage("Successfully Password Reset.")
        gsApiActionDefinition.successResponseAsData()
        return gsApiActionDefinition
    }


    GsApiActionDefinition activeInactive(){
        GsApiActionDefinition gsApiActionDefinition = new GsApiActionDefinition<User>(User)
        gsApiActionDefinition.customProcessor = new CustomProcessor() {
            @Override
            GsApiResponseData process(GsApiActionDefinition actionDefinition, GsParamsPairData paramData, ApiHelper apiHelper) {
                return userService.activeInactive(actionDefinition, paramData, apiHelper)
            }
        }
        gsApiActionDefinition.successResponseFormat = GsApiResponseData.successMessage("Operation Success")
        gsApiActionDefinition.successResponseAsData()
        gsApiActionDefinition.addRequestProperty("id")
        gsApiActionDefinition.addRequestProperty("uuid")
        return gsApiActionDefinition
    }


}
