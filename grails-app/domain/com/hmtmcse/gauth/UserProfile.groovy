package com.hmtmcse.gauth

import com.hmtmcse.gcommon.DomainTask

class UserProfile implements DomainTask {

    Integer id
    Boolean isActive = true
    Boolean isDeleted = false
    Date dateCreated
    Date lastUpdated
    String uuid

    static belongsTo = [user: User]
}
