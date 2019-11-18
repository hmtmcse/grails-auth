package com.hmtmcse.gauth

import com.hmtmcse.gcommon.DomainTask

class UserAccessGroup implements DomainTask {

    Integer id
    Boolean isActive = true
    Boolean isDeleted = false
    Date dateCreated
    Date lastUpdated
    String uuid

    String name
    String identifier
    Set<User> users = []
    Set<UserAccess> userAccesses = []

    static hasMany = [users: User, userAccesses: UserAccess]

    static constraints = {
        identifier(unique: true, nullable: true)
    }
}
