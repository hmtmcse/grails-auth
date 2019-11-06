package com.hmtmcse.gauth.definition

import com.hmtmcse.gauth.User
import com.hmtmcse.gauth.UserService
import com.hmtmcse.gs.GsApiActionDefinition
import com.hmtmcse.gs.GsRequestParamException
import com.hmtmcse.gs.data.GsApiRequestProperty
import com.hmtmcse.gs.data.GsParamsPairData
import com.hmtmcse.gs.model.CustomRequestParamProcessor


class UserDefinitionService {

    UserService userService


    GsApiActionDefinition createUpdate(){
        GsApiActionDefinition gsApiActionDefinition = new GsApiActionDefinition<User>(User)
        gsApiActionDefinition.addRequestProperty("firstName").required()
        gsApiActionDefinition.addRequestProperty("lastName")
        gsApiActionDefinition.addRequestProperty("email").required().customRequestParamProcessor = new CustomRequestParamProcessor() {
            @Override
            Object process(String fieldName, GsParamsPairData gsParamsPairData, GsApiRequestProperty propertyDefinition) throws GsRequestParamException {
                String email = gsParamsPairData.params.email
                if (userService.isEmailExist(email)){
                    throw new GsRequestParamException(email + " Email already exists. Please try with other Email.")
                }
                return email
            }
        }
        return gsApiActionDefinition
    }


    GsApiActionDefinition read(){
        GsApiActionDefinition gsApiActionDefinition = new GsApiActionDefinition<User>(User)
        return gsApiActionDefinition
    }


    GsApiActionDefinition create() {
        GsApiActionDefinition gsApiActionDefinition = createUpdate()
        gsApiActionDefinition.addRequestProperty("password").required()
        return gsApiActionDefinition
    }


    GsApiActionDefinition update() {
        GsApiActionDefinition gsApiActionDefinition = createUpdate()
        return gsApiActionDefinition
    }


    GsApiActionDefinition delete() {
        GsApiActionDefinition gsApiActionDefinition = new GsApiActionDefinition<User>(User)
        return gsApiActionDefinition
    }

    GsApiActionDefinition hardDelete() {
        GsApiActionDefinition gsApiActionDefinition = new GsApiActionDefinition<User>(User)
        return gsApiActionDefinition
    }

    GsApiActionDefinition list() {
        GsApiActionDefinition gsApiActionDefinition = read()
        return gsApiActionDefinition
    }

    GsApiActionDefinition details() {
        GsApiActionDefinition gsApiActionDefinition = read()
        return gsApiActionDefinition
    }

    GsApiActionDefinition activeInactive() {
        GsApiActionDefinition gsApiActionDefinition = new GsApiActionDefinition<User>(User)
        return gsApiActionDefinition
    }

}
