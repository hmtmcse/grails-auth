package com.hmtmcse.gauth

import com.hmtmcse.gcommon.DomainTask
import com.hmtmcse.org.InstanceIdentity
import grails.gorm.MultiTenant
import org.grails.datastore.mapping.core.connections.ConnectionSource


class User implements DomainTask {

    Integer id
    Boolean isActive = true
    Boolean isDeleted = false
    Boolean isOwner = false
    Date dateCreated
    Date lastUpdated
    String uuid


    String firstName
    String lastName
    String email
    String password
    String profilePicture
    String message
    Set<UserAccessGroup> userAccessGroups = []
    Set<UserProfile> userProfile = []
    InstanceIdentity instanceIdentity

    static hasMany = [userAccessGroups: UserAccessGroup, userProfile: UserProfile]

    static constraints = {
        message(nullable: true)
        lastName(nullable: true)
        instanceIdentity(nullable: true)
        profilePicture(nullable: true)
        email(unique: true, nullable: false)
    }

    def beforeInsert() {
        this.password = this.password.encodeAsMD5()
    }
}
