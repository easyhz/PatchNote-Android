package com.easyhz.patchnote.data.datasource.local.configuration

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.data.di.config.ConfigurationDataStore
import com.easyhz.patchnote.data.di.config.ConfigurationKey
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConfigurationLocalDataSourceImpl @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    @ConfigurationDataStore private val dataStore: DataStore<Preferences>
): ConfigurationLocalDataSource {
    private val password = stringPreferencesKey(ConfigurationKey.PASSWORD.key)
    private val isEntered = booleanPreferencesKey(ConfigurationKey.IS_ENTERED.key)

    override suspend fun getPassword(): Result<String?> = withContext(dispatcher) {
        runCatching {
            val preferences = dataStore.data.first()
            return@runCatching preferences[password]
        }
    }

    override suspend fun updatePassword(password: String): Unit = withContext(dispatcher) {
        dataStore.edit { preferences ->
            preferences[this@ConfigurationLocalDataSourceImpl.password] = password
        }
    }

    override suspend fun checkEnteredPassword(): Result<Boolean> = withContext(dispatcher) {
        runCatching {
            val preferences = dataStore.data.first()
            return@runCatching preferences[isEntered] ?: false
        }
    }

    override suspend fun updateEnteredPassword(isEntered: Boolean): Unit = withContext(dispatcher) {
        dataStore.edit { preferences ->
            preferences[this@ConfigurationLocalDataSourceImpl.isEntered] = isEntered
        }
    }
}