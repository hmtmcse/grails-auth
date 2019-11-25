package com.hmtmcse.gauth.definition

import com.hmtmcse.gauth.User
import com.hmtmcse.gauth.UserAccessGroup
import com.hmtmcse.gs.GsApiActionDefinition
import com.hmtmcse.gs.data.GsFilteredData
import com.hmtmcse.gs.model.RequestPreProcessor


class AccessGroupDefinitionService {

    GsApiActionDefinition createUpdate(){
        GsApiActionDefinition gsApiActionDefinition = new GsApiActionDefinition<User>(User)
        return gsApiActionDefinition
    }

    GsApiActionDefinition groupList(){
        GsApiActionDefinition gsApiActionDefinition = new GsApiActionDefinition<UserAccessGroup>(UserAccessGroup)
        gsApiActionDefinition.addResponseProperty("name")
        gsApiActionDefinition.addResponseProperty("id")
        gsApiActionDefinition.addResponseProperty("uuid")
        gsApiActionDefinition.requestPreProcessor = new RequestPreProcessor() {
            @Override
            GsFilteredData process(GsApiActionDefinition definition, GsFilteredData gsFilteredData) {
                definition.addToWhereFilterProperty("isDeleted")
                gsFilteredData.where.addEqual("isDeleted", false)
                definition.addToWhereFilterProperty("isActive")
                gsFilteredData.where.addEqual("isActive", true)
                return gsFilteredData
            }
        }
        return gsApiActionDefinition
    }

    GsApiActionDefinition read(){
        GsApiActionDefinition gsApiActionDefinition = new GsApiActionDefinition<UserAccessGroup>(UserAccessGroup)

        return gsApiActionDefinition
    }

    GsApiActionDefinition create() {
        GsApiActionDefinition gsApiActionDefinition = createUpdate()
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
