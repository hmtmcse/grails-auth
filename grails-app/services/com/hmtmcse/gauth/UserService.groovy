package com.hmtmcse.gauth

import com.hmtmcse.gs.GsApiActionDefinition
import com.hmtmcse.gs.data.ApiHelper
import com.hmtmcse.gs.data.GsApiResponseData
import com.hmtmcse.gs.data.GsParamsPairData
import grails.gorm.transactions.Transactional


class UserService {

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

    @Transactional
    GsApiResponseData activeInactive(GsApiActionDefinition actionDefinition, GsParamsPairData paramData, ApiHelper apiHelper) {
        User user = null
        if (paramData.filteredGrailsParameterMap.id){
            user = getById(paramData.filteredGrailsParameterMap.id)
        }else if (paramData.filteredGrailsParameterMap.uuid){
            user = getById(paramData.filteredGrailsParameterMap.uuid)
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


}
