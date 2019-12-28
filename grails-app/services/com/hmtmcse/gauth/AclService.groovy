package com.hmtmcse.gauth

import grails.gorm.transactions.Transactional


class AclService {

    @Transactional
    def addAcl(List listOfMapAcl, String groupName = AuthConstant.GROUP_ADMINISTRATOR) {
        UserAccess userAccess
        UserAccessGroup userAccessGroup = UserAccessGroup.findByIdentifier(groupName)
        listOfMapAcl.each { access ->
            userAccess = new UserAccess(access)
            userAccess.isAllowedAllAction = true
            userAccess.userAccessGroup = userAccessGroup
            userAccess.actionName = [:]
            userAccess.save()
        }
    }

}
