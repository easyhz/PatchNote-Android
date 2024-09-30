package com.easyhz.patchnote.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.easyhz.patchnote.data.di.config.userDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataStoreModule {
    companion object {

        @Singleton
        @Provides
        fun provideUserDataStorePreferences(
            @ApplicationContext context: Context
        ): DataStore<Preferences> =
            context.userDataStore
    }
}