package com.hmtmcse.gauth

import com.hmtmcse.gcommon.DomainTask
import grails.gorm.MultiTenant


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
    Set<UserProfile> userProfile = []


    static belongsTo = [UserAccessGroup]
    static hasMany = [userAccessLists: UserAccessList, userAccessGroups: UserAccessGroup, userProfile: UserProfile]

    static constraints = {
        message(nullable: true)
        lastName(nullable: true)
        profilePicture(nullable: true)
        email(unique: true, nullable: false)
    }

    def beforeInsert() {
        this.password = this.password.encodeAsMD5()
    }
}
