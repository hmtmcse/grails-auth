package com.hmtmcse.common

import com.hmtmcse.gcommon.DomainTask
import grails.gorm.MultiTenant

class Settings implements MultiTenant<Settings>, DomainTask {

    Integer id
    Boolean isActive = true
    Boolean isDeleted = false
    Date dateCreated
    Date lastUpdated
    String uuid

    String settingGroup
    String key
    String value
    String otherInfo
    String description
    String json

    static constraints = {
        description(nullable: true)
        json(nullable: true)
        otherInfo(nullable: true)
    }

    static mapping = {
        description(type: "text")
        json(type: "text")
        otherInfo(type: "text")
    }
}


