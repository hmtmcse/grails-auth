package com.hmtmcse.gauth

import com.hmtmcse.define.NavDef
import com.hmtmcse.define.NavItemDef
import grails.gorm.transactions.Transactional


class AuthInitializationService {

    @Transactional
    def aclInit() {

        List navigation = [
                [
                        displayName: "Dashboard",
                        name: "dashboard",
                        url: "/dashboard",
                        controllerName: "apiUserV1",
                        isAllowedAllAction: true,
                        icon: "",
                        actions: [:],
                ],
                [
                        displayName: "User",
                        name: "user",
                        url: "/user",
                        groupDisplayName: "Access Control",
                        groupName: "access-control",
                        controllerName: "apiUserV1",
                        isAllowedAllAction: false,
                        icon: "",
                        actions: [
                                [
                                        displayName: "Details",
                                        actionName: [
                                                "postDetails": true,
                                                "getDetails": true,
                                        ]
                                ],
                                [
                                        displayName: "List",
                                        actionName: [
                                                "postList": true,
                                                "getList": true
                                        ]
                                ],
                                [
                                        displayName: "Create",
                                        actionName: [
                                                "postCreate": true
                                        ]
                                ],
                                [
                                        displayName: "Delete",
                                        actionName: [
                                                "deleteDelete": true
                                        ]
                                ],
                                [
                                        displayName: "Update",
                                        actionName: [
                                                "postUpdate": true
                                        ]
                                ],
                                [
                                        displayName: "Active Inactive",
                                        actionName: [
                                                "getActiveInactive": true
                                        ]
                                ]
                        ],
                ]
        ]

        if (NavDef.count() == 0){
            navigation.each {it ->
                NavDef navDef = new NavDef(it)
                navDef.save()
                if (navDef.hasErrors()){
                    navDef.errors.each {
                        println(it)
                    }
                }else if (it.actions){
                    NavItemDef navItemDef
                    it.actions.each { action ->
                        navItemDef = new NavItemDef(action)
                        navItemDef.navDef = navDef
                        navItemDef.actionName = action.actionName
                        navItemDef.save()
                        if (navItemDef.hasErrors()){
                            navItemDef.errors.each {
                                println(it)
                            }
                        }
                    }
                }
            }
        }



        if (UserAccessGroup.count() == 0){
            UserAccess userAccess
            UserAccessGroup userAccessGroup =  new UserAccessGroup(name: "Administrator", identifier: AuthConstant.GROUP_ADMINISTRATOR)
            userAccessGroup.save()
            navigation.each { access ->
                userAccess = new UserAccess(access)
                userAccess.isAllowedAllAction = true
                userAccess.userAccessGroup = userAccessGroup
                userAccess.actionName = [:]
                userAccess.save()
            }

            userAccessGroup =  new UserAccessGroup(name: "Manager", identifier: AuthConstant.GROUP_MANAGER)
            userAccessGroup.save()
            navigation.each { access ->
                userAccess = new UserAccess(access)
                userAccess.userAccessGroup = userAccessGroup
                Map actionName = [:]
                if (access.actions){
                    access.actions.each {
                        if (it.displayName.contains("Delete")){
                            return
                        }
                        actionName += it.actionName
                    }
                }
                userAccess.actionName = actionName
                userAccess.save()
            }


            userAccessGroup =  new UserAccessGroup(name: "User", identifier: AuthConstant.GROUP_USER)
            userAccessGroup.save()
            navigation.each { access ->
                if (access.displayName == "User"){
                    return
                }
                userAccess = new UserAccess(access)
                userAccess.userAccessGroup = userAccessGroup
                Map actionName = [:]
                if (access.actions){
                    access.actions.each {
                        if (it.displayName.contains("Delete") || it.displayName.contains("Create") || it.displayName.contains("Update")){
                            return
                        }
                        actionName += it.actionName
                    }
                }
                userAccess.actionName = actionName
                userAccess.save()
            }

        }

        List userMap = [
                [
                        firstName: "Administrator",
                        email: "admin@grails786.com",
                        password: "admin",
                        identifier: AuthConstant.GROUP_ADMINISTRATOR
                ],
                [
                        firstName: "Manager",
                        email: "manager@grails786.com",
                        password: "manager",
                        identifier: AuthConstant.GROUP_MANAGER
                ],
                [
                        firstName: "User",
                        email: "user@grails786.com",
                        password: "user",
                        identifier: AuthConstant.GROUP_USER
                ]
        ]

        User user
        if (User.count() == 0){
            userMap.each {
                user = new User(it)
                user.userAccessGroup = UserAccessGroup.findByIdentifier(it.identifier)
                user.save()
            }
        }


    }
}
