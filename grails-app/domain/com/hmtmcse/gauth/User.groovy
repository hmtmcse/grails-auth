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
    String message


    static constraints = {
        message(nullable: true)
        email(unique: true, email: true, nullable: false)
    }
}
