package com.easyhz.patchnote.data.datasource.local.user

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.core.model.user.User
import com.easyhz.patchnote.data.di.config.UserKey
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val dataStore: DataStore<Preferences>
): UserLocalDataSource {
    private val userId = stringPreferencesKey(UserKey.USER_ID.key)
    private val userName = stringPreferencesKey(UserKey.USER_NAME.key)
    private val userPhone = stringPreferencesKey(UserKey.USER_PHONE.key)
    private val userTeamId = stringPreferencesKey(UserKey.USER_TEAM_ID.key)

    override suspend fun updateUser(user: User): Unit = withContext(dispatcher) {
        dataStore.edit { preferences ->
            preferences[userId] = user.id
            preferences[userName] = user.name
            preferences[userPhone] = user.phone
            preferences[userTeamId] = user.teamId
        }
    }

    override suspend fun getUser(): Result<User> = withContext(dispatcher) {
        runCatching {
            val preferences = dataStore.data.first()
            return@runCatching User(
                id = preferences[userId] ?: throw generateNullException(UserKey.USER_ID),
                name = preferences[userName] ?: throw generateNullException(UserKey.USER_NAME),
                phone = preferences[userPhone] ?: throw generateNullException(UserKey.USER_PHONE),
                teamId = preferences[userTeamId] ?: throw generateNullException(UserKey.USER_TEAM_ID)
            )
        }
    }


    private fun generateNullException(userKey: UserKey): Exception {
        return Exception("${userKey.key} is null")
    }
}