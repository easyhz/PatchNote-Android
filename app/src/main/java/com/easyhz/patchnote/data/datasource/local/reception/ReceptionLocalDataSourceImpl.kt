package com.easyhz.patchnote.data.datasource.local.reception

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.data.di.config.ReceptionSettingDataStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ReceptionLocalDataSourceImpl @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    @ReceptionSettingDataStore private val dataStore: DataStore<Preferences>
): ReceptionLocalDataSource {
    private val site = booleanPreferencesKey(CategoryType.SITE.alias)
    private val building = booleanPreferencesKey(CategoryType.BUILDING.alias)
    private val unit = booleanPreferencesKey(CategoryType.UNIT.alias)
    private val space = booleanPreferencesKey(CategoryType.SPACE.alias)
    private val part = booleanPreferencesKey(CategoryType.PART.alias)
    private val workType = booleanPreferencesKey(CategoryType.WORK_TYPE.alias)

    override fun getReceptionSetting(): Flow<LinkedHashMap<CategoryType, Boolean>> =
        dataStore.data.map { preferences ->
            linkedMapOf(
                CategoryType.SITE to (preferences[site] ?: true),
                CategoryType.BUILDING to (preferences[building] ?: true),
                CategoryType.UNIT to (preferences[unit] ?: true),
                CategoryType.SPACE to (preferences[space] ?: true),
                CategoryType.PART to (preferences[part] ?: false),
                CategoryType.WORK_TYPE to (preferences[workType] ?: false),
            )
        }.flowOn(dispatcher)

    override suspend fun setReceptionSetting(categoryType: CategoryType, newValue: Boolean): Unit = withContext(dispatcher) {
        dataStore.edit { preferences ->
            val item = booleanPreferencesKey(categoryType.alias)
            preferences[item] = newValue
        }
    }
}