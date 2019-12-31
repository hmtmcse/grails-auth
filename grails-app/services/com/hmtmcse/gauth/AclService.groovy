package com.hmtmcse.gauth

import grails.gorm.transactions.Transactional


class AclService {

    AuthInitializationService authInitializationService

    @Transactional
    def addAcl(List listOfMapAcl, String groupName = AuthConstant.GROUP_ADMINISTRATOR) {
        UserAccess userAccess
        UserAccessGroup userAccessGroup = UserAccessGroup.findByIdentifier(groupName)
        if (!userAccessGroup) {
            authInitializationService.aclInit()
            userAccessGroup = UserAccessGroup.findByIdentifier(groupName)
        }
        listOfMapAcl.each { access ->
            userAccess = new UserAccess(access)
            userAccess.isAllowedAllAction = true
            userAccess.userAccessGroup = userAccessGroup
            userAccess.actionName = [:]
            userAccess.save()
            if (userAccess.hasErrors()){
                userAccess.errors.each {
                    println("ACL Save ERROR: ${it}")
                }
            }
            if (!userAccess.userAccessGroup){
                println("Invalid UserAccessGroup For: ${userAccess.displayName} / ${userAccess.groupDisplayName}")
            }
        }
    }

}
