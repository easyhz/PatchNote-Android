package com.easyhz.patchnote.data.datasource.local.user

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.core.common.error.AppError
import com.easyhz.patchnote.core.common.util.serializable.SerializableHelper
import com.easyhz.patchnote.core.common.util.serializable.deserializeList
import com.easyhz.patchnote.core.model.user.User
import com.easyhz.patchnote.data.di.config.UserDataStore
import com.easyhz.patchnote.data.di.config.UserKey
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    @UserDataStore private val dataStore: DataStore<Preferences>,
    private val serializableHelper: SerializableHelper,
): UserLocalDataSource {
    private val userId = stringPreferencesKey(UserKey.USER_ID.key)
    private val userName = stringPreferencesKey(UserKey.USER_NAME.key)
    private val userPhone = stringPreferencesKey(UserKey.USER_PHONE.key)
    private val userTeamId = stringPreferencesKey(UserKey.USER_TEAM_ID.key)
    private val isFirstOpen = booleanPreferencesKey(UserKey.IS_FIRST_OPEN.key)
    private val userTeamName = stringPreferencesKey(UserKey.USER_TEAM_NAME.key)
    private val userTeams = stringPreferencesKey(UserKey.USER_TEAMS.key)
    private val isOfflineFirstOpen = booleanPreferencesKey(UserKey.IS_OFFLINE_FIRST_OPEN.key)

    override suspend fun updateUser(user: User): Unit = withContext(dispatcher) {
        dataStore.edit { preferences ->
            preferences[userId] = user.id
            preferences[userName] = user.name
            preferences[userPhone] = user.phone
            user.currentTeamId?.let { preferences[userTeamId] = it }
            preferences[userTeams] = serializableHelper.serialize(user.teamIds, List::class.java)
        }
    }

    override suspend fun getUser(): Result<User> = withContext(dispatcher) {
        runCatching {
            val preferences = dataStore.data.first()
            return@runCatching User(
                id = preferences[userId] ?: throw generateNullException(UserKey.USER_ID),
                name = preferences[userName] ?: throw generateNullException(UserKey.USER_NAME),
                phone = preferences[userPhone] ?: throw generateNullException(UserKey.USER_PHONE),
                currentTeamId = preferences[userTeamId],
                teamIds = serializableHelper.deserializeList(preferences[userTeams] ?: "") ?: emptyList()
            )
        }
    }

    override suspend fun deleteUser(): Unit = withContext(dispatcher) {
        dataStore.edit { preferences ->
            preferences.remove(userId)
            preferences.remove(userName)
            preferences.remove(userPhone)
            preferences.remove(userTeamId)
            preferences.remove(userTeams)
        }
    }

    override suspend fun isFirstOpen(): Result<Boolean> = withContext(dispatcher) {
        runCatching {
            val preferences = dataStore.data.first()
            return@runCatching preferences[isFirstOpen] ?: true
        }
    }

    override suspend fun setIsFirstOpen(newValue: Boolean) {
        dataStore.edit { preferences ->
            preferences[isFirstOpen] = newValue
        }
    }

    override suspend fun updateTeamName(teamName: String) {
        dataStore.edit { preferences ->
            preferences[userTeamName] = teamName
        }
    }

    override suspend fun getTeamName(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[userTeamName] ?: "PatchNote"
        }
    }

    override suspend fun deleteTeamName() {
        dataStore.edit { preferences ->
            preferences.remove(userTeamName)
        }
    }

    override suspend fun isOfflineFirstOpen(): Result<Boolean> = withContext(dispatcher) {
        runCatching {
            val preferences = dataStore.data.first()
            return@runCatching preferences[isOfflineFirstOpen] ?: true
        }
    }

    override suspend fun setIsOfflineFirstOpen(newValue: Boolean) {
        dataStore.edit { preferences ->
            preferences[isOfflineFirstOpen] = newValue
        }
    }

    override suspend fun setCurrentTeamId(newValue: String) {
        dataStore.edit { preferences ->
            preferences[userTeamId] = newValue
        }
    }

    override suspend fun getCurrentTeamId(): Result<String?> {
        return runCatching {
            val preferences = dataStore.data.first()
            preferences[userTeamId]
        }
    }

    private fun generateNullException(userKey: UserKey): Exception {
        return AppError.DefaultError("${userKey.key} is null")
    }

}