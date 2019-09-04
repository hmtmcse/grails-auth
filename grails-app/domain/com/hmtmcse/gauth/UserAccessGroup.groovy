package com.hmtmcse.gauth

class UserAccessGroup {

    Integer id
    Boolean isActive  = true
    Boolean isDeleted = false
    Date dateCreated
    Date lastUpdated

    static constraints = {
    }
}
