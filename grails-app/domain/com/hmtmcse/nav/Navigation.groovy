package com.hmtmcse.nav

import com.hmtmcse.gauth.User
import com.hmtmcse.gauth.UserAccessGroup
import com.hmtmcse.gcommon.DomainTask
import grails.gorm.MultiTenant

class Navigation  implements MultiTenant<Navigation>, DomainTask {

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
    Map<String, Boolean> actionName = [:]

    Navigation parent
    UserAccessGroup userAccessGroup
    User user

    static hasMany = [navigations: Navigation]

    static constraints = {
        url(nullable: true)
        parent(nullable: true)
        userAccessGroup(nullable: true)
        user(nullable: true)
    }

}
