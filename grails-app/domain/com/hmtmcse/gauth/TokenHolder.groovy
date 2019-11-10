package com.hmtmcse.gauth

import com.hmtmcse.gcommon.DomainTask

class TokenHolder implements DomainTask {

    Integer id
    Boolean isActive = true
    Date dateCreated
    Date lastUpdated
    String uuid
    String token
    Long timeMillis
    User user

    static constraints = {}

}
