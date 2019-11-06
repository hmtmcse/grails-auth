package com.hmtmcse.gauth.definition

import com.hmtmcse.gauth.User
import com.hmtmcse.gs.GsApiActionDefinition


class UserDefinitionService {


    GsApiActionDefinition createUpdate(){
        GsApiActionDefinition gsApiActionDefinition = new GsApiActionDefinition<User>(User)
        gsApiActionDefinition.addRequestProperty("firstName").required()
        gsApiActionDefinition.addRequestProperty("lastName")
        gsApiActionDefinition.addRequestProperty("profilePicture")
        gsApiActionDefinition.addRequestProperty("email").required()
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
