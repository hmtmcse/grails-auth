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

    Integer navOrder = 0
    String displayName
    String name
    String url
    String icon
    String groupDisplayName
    String groupName
    String groupUrl

    String controllerName
    Boolean isAllowedAllAction = false
    NavDef parent


    static hasMany = [navigations: NavDef]

    static constraints = {
        icon(nullable: true)
        url(nullable: true)
        parent(nullable: true)
        groupName(nullable: true)
        groupDisplayName(nullable: true)
        groupUrl(nullable: true)
    }

}
