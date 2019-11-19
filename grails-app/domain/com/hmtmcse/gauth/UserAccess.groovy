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

    String groupDisplayName
    String groupName
    String groupUrl
    String displayName
    String name
    String url
    String icon

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
        icon(nullable: true)
        url(nullable: true)
        name(nullable: true)
        groupDisplayName(nullable: true)
        groupName(nullable: true)
        groupUrl(nullable: true)
        user(nullable: true)
        userAccessGroup(nullable: true)
        filterParamName(nullable: true)
    }
}
