package com.hmtmcse.org

import com.hmtmcse.gauth.User
import com.hmtmcse.gcommon.DomainTask

class InstanceIdentity implements DomainTask {

    Integer id
    Boolean isActive = true
    Boolean isDeleted = false
    Boolean isSuspended = false
    Date dateCreated
    Date started
    Date lastUpdated
    String uuid
    String message

    String name
    String ownerName
    String ownerEmail
    String ownerMobile
    String ownerPhone
    String address
    String identity
    String encryptionHash

    static hasMany = [user: User]


    static constraints = {
        ownerName(nullable: true)
        ownerMobile(nullable: true)
        ownerPhone(nullable: true)
        address(nullable: true)
        message(nullable: true)
        started(nullable: true)

        identity(unique: true)
        ownerEmail(unique: true)
    }
}
