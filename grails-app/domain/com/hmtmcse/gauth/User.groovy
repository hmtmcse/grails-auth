package com.hmtmcse.gauth

class User {

    Integer id
    Boolean isActive  = true
    Boolean isDeleted = false
    Date dateCreated
    Date lastUpdated
    String firstName
    String lastName
    String email
    String password
    String message


    static constraints = {
        message(nullable: true)
    }
}
