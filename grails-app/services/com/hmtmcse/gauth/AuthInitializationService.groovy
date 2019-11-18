package com.hmtmcse.gauth


class AuthInitializationService {

    def aclInit() {

        List group = [
                [
                        name      : "Administrator",
                        identifier: AuthConstant.GROUP_ADMINISTRATOR
                ],
                [
                        name      : "Manager",
                        identifier: AuthConstant.GROUP_MANAGER
                ],
                [
                        name      : "User",
                        identifier: AuthConstant.GROUP_USER
                ]
        ]

        UserAccessGroup userAccessGroup
        if (UserAccessGroup.count() == 0){
            group.each {
                userAccessGroup = new UserAccessGroup(it)
                userAccessGroup.save()
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
                user.addToUserAccessGroups(UserAccessGroup.findByIdentifier(it.identifier))
                user.save()
            }
        }

        List navigation = [
                [
                        displayName: "Dashboard",
                        name: "dashboard",
                        url: "",
                        controllerName: "",
                        isAllowedAllAction: true,
                        actions: [:],
                ],
                [
                        displayName: "User",
                        name: "user",
                        url: "",
                        controllerName: "apiUserV1",
                        isAllowedAllAction: false,
                        actions: [
                                [
                                        name: "Details",
                                        actionName: [
                                                "postDetails": true,
                                                "getDetails": true,
                                        ]
                                ],
                                [
                                        name: "List",
                                        actionName: [
                                                "postList": true,
                                                "getList": true
                                        ]
                                ],
                                [
                                        name: "Create",
                                        actionName: [
                                                "postCreate": true
                                        ]
                                ],
                                [
                                        name: "Delete",
                                        actionName: [
                                                "deleteDelete": true
                                        ]
                                ],
                                [
                                        name: "Update",
                                        actionName: [
                                                "postUpdate": true
                                        ]
                                ],
                                [
                                        name: "Active Inactive",
                                        actionName: [
                                                "getActiveInactive": true
                                        ]
                                ]
                        ],
                ]
        ]

    }
}
