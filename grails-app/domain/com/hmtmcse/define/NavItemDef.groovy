package com.hmtmcse.define

import com.hmtmcse.gcommon.DomainTask
import grails.gorm.MultiTenant

class NavItemDef implements MultiTenant<NavItemDef>, DomainTask {

    Integer id
    Boolean isActive = true
    Boolean isDeleted = false
    Date dateCreated
    Date lastUpdated
    String uuid

    String displayName

    Map<String, Boolean> actionName = [:]
    NavDef navDef


    static belongsTo = [navDef: NavDef]

    static constraints = {}

}
