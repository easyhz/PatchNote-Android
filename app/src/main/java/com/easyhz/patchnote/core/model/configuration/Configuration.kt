package com.easyhz.patchnote.core.model.configuration

data class Configuration(
    val androidVersion: String,
    var notionUrl: String,
    val settingPassword: String,
    val maintenanceNotice: String,
)
