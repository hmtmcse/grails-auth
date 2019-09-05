package com.hmtmcse.gauth

import com.hmtmcse.gcommon.DomainTask


class User implements DomainTask {

    Integer id
    Boolean isActive = true
    Boolean isDeleted = false
    Date dateCreated
    Date lastUpdated
    String uuid


    String firstName
    String lastName
    String email
    String password
    String profilePicture
    String message
    Set<UserAccessList> userAccessLists = []
    Set<UserAccessGroup> userAccessGroups = []


    static belongsTo = [UserAccessGroup]
    static hasMany = [userAccessLists: UserAccessList, userAccessGroups: UserAccessGroup]

    static constraints = {
        message(nullable: true)
        profilePicture(nullable: true)
        email(unique: true, email: true, nullable: false)
    }
}
