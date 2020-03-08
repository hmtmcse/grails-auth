package com.hmtmcse.gauth

import com.hmtmcse.common.AuthSetting
import grails.gorm.transactions.Transactional


class AuthSettingService {

    AuthSetting getSetting(String groupName, String key) {
        return AuthSetting.createCriteria().get {
            eq("settingGroup", groupName)
            eq("dataKey", key)
        }
    }

    List<AuthSetting> getSettings(String groupName) {
        return AuthSetting.createCriteria().get {
            eq("settingGroup", groupName)
        }
    }

    @Transactional
    Boolean cleanJwtKey() {
        def jwt = AuthSetting.createCriteria().list {
            eq("settingGroup", AuthConstant.JWT_SETTING_GROUP)
            eq("dataKey", AuthConstant.JWT_KEY)
        }
        if (jwt) {
            jwt*.delete()
        }
    }

    AuthSetting saveSetting(String groupName, String key, String value) {
        AuthSetting settings = new AuthSetting(settingGroup: groupName, dataKey: key, dataValue: value)
        settings.save()
        return settings
    }

}
