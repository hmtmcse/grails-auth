package com.hmtmcse.gauth

import com.hmtmcse.define.NavDef
import com.hmtmcse.gcommon.TMGUtil
import grails.gorm.transactions.Transactional

class AuthInitializationService {

    NavigationService navigationService
    AuthSettingService authSettingService
    UserService userService


    @Transactional
    def aclInit() {

        if (!authSettingService.getSetting(AuthConstant.JWT_SETTING_GROUP, AuthConstant.JWT_KEY)){
            authSettingService.saveSetting(AuthConstant.JWT_SETTING_GROUP, AuthConstant.JWT_KEY, TMGUtil.uuid())
        }

        List navigation = [
                [
                        displayName       : "Dashboard",
                        name              : "dashboard",
                        url               : "/dashboard",
                        controllerName    : "apiDashboardV1",
                        isAllowedAllAction: true,
                        navOrder          : 0,
                        icon              : "dashboard",
                        actions           : [:],
                ],
                [
                        displayName       : "User",
                        name              : "users",
                        url               : "/users",
                        groupDisplayName  : "Access Control",
                        groupName         : "access-control",
                        controllerName    : "apiUserV1",
                        isAllowedAllAction: false,
                        navOrder          : 99,
                        icon              : "rowing",
                        actions           : [
                                [
                                        displayName: "Details",
                                        actionName : [
                                                "postDetails": true,
                                                "getDetails" : true,
                                        ]
                                ],
                                [
                                        displayName: "List",
                                        actionName : [
                                                "postList": true,
                                                "getList" : true
                                        ]
                                ],
                                [
                                        displayName: "Create",
                                        actionName : [
                                                "postCreate": true
                                        ]
                                ],
                                [
                                        displayName: "Delete",
                                        actionName : [
                                                "deleteDelete": true
                                        ]
                                ],
                                [
                                        displayName: "Update",
                                        actionName : [
                                                "postUpdate": true
                                        ]
                                ],
                                [
                                        displayName: "Active Inactive",
                                        actionName : [
                                                "getActiveInactive": true
                                        ]
                                ]
                        ],
                ]
        ]

        List userMap = [
                [
                        firstName : "Administrator",
                        email     : "admin@grails786.com",
                        password  : "admin",
                        identifier: AuthConstant.GROUP_ADMINISTRATOR
                ],
                [
                        firstName : "Manager",
                        email     : "manager@grails786.com",
                        password  : "manager",
                        identifier: AuthConstant.GROUP_MANAGER
                ],
                [
                        firstName : "User",
                        email     : "user@grails786.com",
                        password  : "user",
                        identifier: AuthConstant.GROUP_USER
                ]
        ]


        if (UserAccessGroup.count() == 0) {
            UserAccess userAccess
            UserAccessGroup userAccessGroup = new UserAccessGroup(name: "Administrator", identifier: AuthConstant.GROUP_ADMINISTRATOR)
            userAccessGroup.save()
            navigation.each { access ->
                userAccess = new UserAccess(access)
                userAccess.isAllowedAllAction = true
                userAccess.userAccessGroup = userAccessGroup
                userAccess.actionName = [:]
                userAccess.save()
            }

            userAccessGroup = new UserAccessGroup(name: "Manager", identifier: AuthConstant.GROUP_MANAGER)
            userAccessGroup.save()
            navigation.each { access ->
                userAccess = new UserAccess(access)
                userAccess.userAccessGroup = userAccessGroup
                Map actionName = [:]
                if (access.actions) {
                    access.actions.each {
                        if (it.displayName.contains("Delete")) {
                            return
                        }
                        actionName += it.actionName
                    }
                }
                userAccess.actionName = actionName
                userAccess.save()
            }


            userAccessGroup = new UserAccessGroup(name: "User", identifier: AuthConstant.GROUP_USER)
            userAccessGroup.save()
            navigation.each { access ->
                if (access.displayName == "User") {
                    return
                }
                userAccess = new UserAccess(access)
                userAccess.userAccessGroup = userAccessGroup
                Map actionName = [:]
                if (access.actions) {
                    access.actions.each {
                        if (it.displayName.contains("Delete") || it.displayName.contains("Create") || it.displayName.contains("Update")) {
                            return
                        }
                        actionName += it.actionName
                    }
                }
                userAccess.actionName = actionName
                userAccess.save()
            }

        }

        userService.insertUser(userMap)

        if (NavDef.count() == 0) {
            navigationService.addNavigation(navigation)
        }
    }
}
