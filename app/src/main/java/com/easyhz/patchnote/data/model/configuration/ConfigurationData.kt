package com.easyhz.patchnote.data.model.configuration

import com.google.firebase.firestore.PropertyName

data class ConfigurationData(
    @PropertyName("androidVersion")
    val androidVersion: String = "",
    @get:PropertyName("notionURL")
    @set:PropertyName("notionURL")
    var notionUrl: String = "",
    @PropertyName("settingPassword")
    val settingPassword: String = "",
)
