package com.easyhz.patchnote.data.datasource.local.image

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.core.common.util.toLinkedHashMap
import com.easyhz.patchnote.core.model.image.DisplayImageType
import com.easyhz.patchnote.data.di.config.ImageSettingDataStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ImageLocalDataSourceImpl @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    @ImageSettingDataStore private val dataStore: DataStore<Preferences>
): ImageLocalDataSource {
    private val displayImageTypePreferencesKey = DisplayImageType.entries.map {
        booleanPreferencesKey(it.alias)
    }

    override fun getImageSetting(): Flow<LinkedHashMap<DisplayImageType, Boolean>> =
        dataStore.data.map { preferences ->
            DisplayImageType.entries
                .associateWith { type ->
                    preferences[displayImageTypePreferencesKey[type.ordinal]] ?: type.defaultOption
                }.toLinkedHashMap()
        }.flowOn(dispatcher)

    override suspend fun setImageSetting(categoryType: DisplayImageType, newValue: Boolean) {
        dataStore.edit { preferences ->
            val item = booleanPreferencesKey(categoryType.alias)
            preferences[item] = newValue
        }
    }
}