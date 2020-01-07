package com.hmtmcse.gauth

import com.hmtmcse.common.AuthSetting


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

    AuthSetting saveSetting(String groupName, String key, String value) {
        AuthSetting settings = new AuthSetting(settingGroup: groupName, dataKey: key, dataValue: value)
        settings.save()
        return settings
    }

}
