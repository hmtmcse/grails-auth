package com.hmtmcse.gauth

import com.hmtmcse.common.Settings


class SettingService {

    Settings getSetting(String groupName, String key) {
        return Settings.findBySettingGroupAndKey(groupName, key)
    }

    List<Settings> getSettings(String groupName) {
        return Settings.findAllBySettingGroup(groupName)
    }

    Settings saveSetting(String groupName, String key, String value) {
        Settings settings = new Settings(settingGroup: groupName, key: key, value: value)
        settings.save()
        return settings
    }

}
