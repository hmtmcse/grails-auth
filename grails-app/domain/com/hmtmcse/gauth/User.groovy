package com.hmtmcse.gauth

import com.hmtmcse.gcommon.DomainTask
import com.hmtmcse.org.InstanceIdentity
import grails.gorm.MultiTenant
import org.grails.datastore.mapping.core.connections.ConnectionSource


class User implements DomainTask {

    Integer id
    Boolean isActive = true
    Boolean isDeleted = false
    Date dateCreated
    Date lastUpdated
    String uuid

    Boolean isOwner = false

    String firstName
    String lastName
    String email
    String password
    String profilePicture
    String message
    InstanceIdentity instanceIdentity
    UserProfile userProfile
    UserAccessGroup userAccessGroup


    static constraints = {
        message(nullable: true)
        lastName(nullable: true)
        instanceIdentity(nullable: true)
        profilePicture(nullable: true)
        userProfile(nullable: true)
        email(unique: true, nullable: false)
    }

    def beforeInsert() {
        this.password = this.password.encodeAsMD5()
    }
}
