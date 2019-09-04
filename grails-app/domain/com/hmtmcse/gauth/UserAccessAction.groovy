package com.hmtmcse.gauth

class UserAccessAction {

    Integer id
    Boolean isActive = true
    Boolean isDeleted = false
    Date dateCreated
    Date lastUpdated
    String actionName
    UserAccessList userAccessList


    static belongsTo = [userAccessList: UserAccessList]

    static constraints = {}
}
