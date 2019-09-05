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
    Set<User> users = []
    Set<UserAccessList> userAccessLists = []


    static hasMany = [users: User, userAccessLists: UserAccessList]

    static constraints = {}
}
