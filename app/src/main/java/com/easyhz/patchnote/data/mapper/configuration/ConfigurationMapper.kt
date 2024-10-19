package com.easyhz.patchnote.data.mapper.configuration

import com.easyhz.patchnote.core.model.configuration.Configuration
import com.easyhz.patchnote.data.model.configuration.ConfigurationData

fun ConfigurationData.toModel() = Configuration(
    androidVersion = androidVersion,
    notionUrl = notionUrl,
    settingPassword = settingPassword
)