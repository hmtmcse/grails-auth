package com.hmtmcse.gauth

import com.hmtmcse.gcommon.DomainTask

class UserAccessAction implements DomainTask {

    Integer id
    Boolean isActive = true
    Boolean isDeleted = false
    Date dateCreated
    Date lastUpdated
    String uuid

    String actionName
    UserAccessList userAccessList


    static belongsTo = [userAccessList: UserAccessList]

    static constraints = {}
}
