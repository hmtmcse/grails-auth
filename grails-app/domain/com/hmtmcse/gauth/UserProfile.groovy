package com.hmtmcse.gauth

import com.hmtmcse.gcommon.DomainTask
import grails.gorm.MultiTenant

class UserProfile implements MultiTenant<UserProfile>, DomainTask {

    Integer id
    Boolean isActive = true
    Boolean isDeleted = false
    Date dateCreated
    Date lastUpdated
    String uuid

    static belongsTo = [user: User]
}
