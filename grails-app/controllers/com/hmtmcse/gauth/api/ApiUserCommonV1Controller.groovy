package com.hmtmcse.gauth.api

import com.hmtmcse.gauth.definition.UserDefinitionService
import com.hmtmcse.gs.GsRestProcessor

class ApiUserCommonV1Controller extends GsRestProcessor {

    UserDefinitionService userDefinitionService


    def getList(){
        return list(userDefinitionService.list())
    }





}
