package com.hmtmcse.gauth

import com.hmtmcse.gauth.jwt.JavaJWTData
import com.hmtmcse.gcommon.TMDateTimeUtil
import com.hmtmcse.gs.GsApiActionDefinition
import com.hmtmcse.gs.data.ApiHelper
import com.hmtmcse.gs.data.GsApiResponseData
import com.hmtmcse.gs.data.GsParamsPairData
import grails.gorm.transactions.Transactional

import java.util.concurrent.TimeUnit


class UserService {

    JwtAuthService jwtAuthService

    User getById(Integer id){
        return User.get(id)
    }


    User getByUuid(String uuid){
        return User.findByUuid(uuid)
    }



    def isEmailExist(String email) {
        User user = User.findByEmail(email)
        if (user){
            return true
        }
        return false
    }

    def getUserById(Long id) {
        return User.createCriteria().get {
            eq("id", id)
            eq("isDeleted", false)
        }
    }

    def allActiveUserList() {
        return User.createCriteria().list {
            and{
                eq("isDeleted", false)
                eq("isActive", true)
            }
        }
    }


    GsApiResponseData restPassword(GsApiActionDefinition actionDefinition, GsParamsPairData paramData, ApiHelper apiHelper) {
        String password = paramData.filteredGrailsParameterMap.password
        String confirmPassword = paramData.filteredGrailsParameterMap.confirmPassword
        if (!password || password.equals("") || !confirmPassword || confirmPassword.equals("") || !password.equals(confirmPassword)){
            return GsApiResponseData.failed("Password and Confirm Password not Matched")
        }
        User user = getUserById(paramData.filteredGrailsParameterMap.id)
        if (!user){
            return GsApiResponseData.failed("Invalid User")
        }
        user.password = password
        user.save()
        if (user.hasErrors()){
            return GsApiResponseData.failed("Unable to Reset password")
        }
        return GsApiResponseData.successMessage("Successfully Password Reset.")
    }


    List<UserAccess> getAllAccess(User user, UserAccessGroup userAccessGroup) {
        List<UserAccess> accesses = UserAccess.createCriteria().list {
            or{
                eq("user", user)
                eq("userAccessGroup", userAccessGroup)
            }
            and {
                eq("isActive", true)
                eq("isDeleted", false)
            }
            order("navOrder", "asc")
        }
        return accesses
    }

    Map getAllAccessMap(User user, UserAccessGroup userAccessGroup) {
        List<UserAccess> accesses = getAllAccess(user, userAccessGroup)
        Map accessMap = [:]
        List navList = []
        Map navMap = [:]
        if (accesses) {
            accesses.each { access ->
                accessMap[access.controllerName] = [
                        isAllowedAllAction: access.isAllowedAllAction,
                        actionName        : access.actionName,
                        filterParamName   : access.filterParamName,
                        filterParamAllowed: access.filterParamAllowed,
                        filterParamDenied : access.filterParamDenied,
                ]
                navMap = [:]
                navMap = [
                        groupDisplayName: access.groupDisplayName ?: "",
                        groupName       : access.groupName ?: "",
                        groupUrl        : access.groupUrl ?: "",
                        displayName     : access.displayName ?: "",
                        name            : access.name ?: "",
                        url             : access.url ?: "",
                        icon            : access.icon ?: "",
                ]
                navList.add(navMap)
            }
        }
        return [
                access : accessMap,
                navList: navList,
        ]
    }


    GsApiResponseData login(GsApiActionDefinition actionDefinition, GsParamsPairData paramData, ApiHelper apiHelper) {
        String email = paramData.filteredGrailsParameterMap.email
        String password = paramData.filteredGrailsParameterMap.password
        User user = User.createCriteria().get {
            and {
                eq("email", email)
                eq("password", password.encodeAsMD5())
                eq("isActive", true)
                eq("isDeleted", false)
            }
        }
        return makeAuthResponse(user, actionDefinition, apiHelper)
    }



    @Transactional
    GsApiResponseData activeInactive(GsApiActionDefinition actionDefinition, GsParamsPairData paramData, ApiHelper apiHelper) {
        User user = null
        if (paramData.filteredGrailsParameterMap.id){
            user = getById(paramData.filteredGrailsParameterMap.id)
        }else if (paramData.filteredGrailsParameterMap.uuid){
            user = getByUuid(paramData.filteredGrailsParameterMap.uuid)
        }

        if (!user){
            return GsApiResponseData.failed("Invalid User")
        }
        user.isActive = !user.isActive
        user.save()
        if (user.hasErrors()){
            return GsApiResponseData.failed("Unable to Change Status")
        }
        return GsApiResponseData.successMessage("Successfully Change Status.")
    }


    GsApiResponseData makeAuthResponse(User user, GsApiActionDefinition actionDefinition, ApiHelper apiHelper){
        Map userInfo = [:]
        if (user){
            userInfo.user = apiHelper.help.responseMapGenerator(actionDefinition.getResponseProperties(), user)
            userInfo.login = jwtAuthService.jwtTokenGen(user)
            userInfo += getAllAccessMap(user, user.userAccessGroup)
            return GsApiResponseData.successResponse(userInfo)
        }
        return apiHelper.help.responseToApi(actionDefinition, user)
    }

    @Transactional
    GsApiResponseData renew(GsApiActionDefinition actionDefinition, GsParamsPairData paramData, ApiHelper apiHelper) {
        String refreshToken = paramData.filteredGrailsParameterMap.refreshToken
        if (refreshToken){
            JavaJWTData javaJWTData = new JavaJWTData()
            Long expire = TimeUnit.MINUTES.toMillis(Long.parseLong(javaJWTData.refreshTokenExpireInMinutes + ""))
            TokenHolder tokenHolder = TokenHolder.findByToken(refreshToken)
            if (tokenHolder && tokenHolder.timeMillis){
                Long current = TMDateTimeUtil.currentMillisecond() - tokenHolder.timeMillis
                if (expire >= current){
                    def data = makeAuthResponse(tokenHolder.user, actionDefinition, apiHelper)
                    tokenHolder.delete()
                    return data
                }
            }
        }
        return makeAuthResponse(null, actionDefinition, apiHelper)
    }

}
