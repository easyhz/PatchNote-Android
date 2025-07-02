 package com.easyhz.patchnote.data.di.config

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore


val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "PatchNote.user"
)

val Context.configurationDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "PatchNote.configuration"
)

val Context.receptionSettingDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "PatchNote.setting.reception"
)

val Context.imageSettingDataStore: DataStore<Preferences> by preferencesDataStore(
 name = "PatchNote.setting.image"
)


internal enum class UserKey(
    val key: String
) {
    USER_ID(
        key = "id"
    ), USER_NAME(
        key = "name"
    ), USER_PHONE(
        key = "phone"
    ), USER_TEAM_ID(
        key = "teamId"
    ), IS_FIRST_OPEN(
        key = "isFirstOpen"
    ), USER_TEAM_NAME(
        key = "teamName"
    ), USER_TEAMS(
        key = "teams"
    ), USER_TEAM_JOIN_DATES(
        key = "teamJoinDates"
    ), USER_CREATED_AT(
        key = "createdAt"
    ), IS_OFFLINE_FIRST_OPEN(
        key = "isOfflineFirstOpen"
    )
}


internal enum class ConfigurationKey(
    val key: String
) {
    PASSWORD(
        key = "password"
    ),
    IS_ENTERED(
        key = "isEntered"
    )
}