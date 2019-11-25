package com.hmtmcse.gauth.api

import com.hmtmcse.gauth.definition.AccessGroupDefinitionService
import com.hmtmcse.gs.GsRestProcessor

class ApiAccessGroupV1Controller extends GsRestProcessor {

    AccessGroupDefinitionService accessGroupDefinitionService

    def postCreate() { }

    def putUpdate(){}

    def deleteDelete(){}

    def deleteHardDelete(){}

    def postList(){}

    def getList(){}

    def postDetails(){}

    def getDetails(){}

    def getActiveInactive(){}


    def getGroupList(){
        return listOnly(accessGroupDefinitionService.groupList())
    }

}
