package com.hmtmcse.define

import com.hmtmcse.gcommon.DomainTask
import grails.gorm.MultiTenant

class NavDef implements MultiTenant<NavDef>, DomainTask {

    Integer id
    Boolean isActive = true
    Boolean isDeleted = false
    Date dateCreated
    Date lastUpdated
    String uuid

    String displayName
    String name
    String url

    String controllerName
    Boolean isAllowedAllAction = false
    NavDef parent


    static hasMany = [navigations: NavDef]

    static constraints = {
        url(nullable: true)
        parent(nullable: true)
    }

}
