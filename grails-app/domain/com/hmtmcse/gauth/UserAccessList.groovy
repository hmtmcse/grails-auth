package com.hmtmcse.gauth

import com.hmtmcse.gcommon.DomainTask

class UserAccessList implements DomainTask {

    Integer id
    Boolean isActive = true
    Boolean isDeleted = false
    Date dateCreated
    Date lastUpdated
    String uuid

    UserAccessGroup userAccessGroup
    User user
    Set<UserAccessAction> userAccessActions = []

    static belongsTo = ['userAccessGroup': UserAccessGroup, 'user': User]
    static hasMany = [userAccessActions: UserAccessAction]

    static constraints = {
        userAccessGroup(nullable: true)
        user(nullable: true)
    }
}
