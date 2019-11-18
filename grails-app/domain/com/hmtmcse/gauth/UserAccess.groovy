package com.hmtmcse.gauth

import com.hmtmcse.gcommon.DomainTask
import grails.gorm.MultiTenant

class UserAccess implements MultiTenant<UserAccess>, DomainTask {

    Integer id
    Boolean isActive = true
    Boolean isDeleted = false
    Date dateCreated
    Date lastUpdated
    String uuid

    String controllerName
    Boolean isAllowedAllAction = false
    Map<String, Boolean> actionName = [:]

    String filterParamName
    Map<String, Boolean> filterParamAllowed = [:]
    Map<String, Boolean> filterParamDenied = [:]

    User user
    UserAccessGroup userAccessGroup

    static belongsTo = [user: User, userAccessGroup: UserAccessGroup]

    static constraints = {
        user(nullable: true)
        userAccessGroup(nullable: true)
        filterParamName(nullable: true)
    }
}
