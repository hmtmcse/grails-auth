package com.hmtmcse.gauth

class UserAccessList {

    Integer id
    Boolean isActive = true
    Boolean isDeleted = false
    Date dateCreated
    Date lastUpdated
    UserAccessGroup userAccessGroup
    User user
    Set<UserAccessAction> userAccessActions = []

    static belongsTo = ['userAccessGroup': UserAccessGroup, 'user': User]
    static hasMany = [userAccessActions: UserAccessAction]

    static constraints = {
        userAccessGroup(nullable: true)
        user(nullable: true)
    }
}
