 package com.easyhz.patchnote.data.di.config

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore


val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "PatchNote.user"
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