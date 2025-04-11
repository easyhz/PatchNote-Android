package com.easyhz.patchnote.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.easyhz.patchnote.data.di.config.ConfigurationDataStore
import com.easyhz.patchnote.data.di.config.ImageSettingDataStore
import com.easyhz.patchnote.data.di.config.ReceptionSettingDataStore
import com.easyhz.patchnote.data.di.config.UserDataStore
import com.easyhz.patchnote.data.di.config.configurationDataStore
import com.easyhz.patchnote.data.di.config.imageSettingDataStore
import com.easyhz.patchnote.data.di.config.receptionSettingDataStore
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
        @UserDataStore
        fun provideUserDataStorePreferences(
            @ApplicationContext context: Context
        ): DataStore<Preferences> =
            context.userDataStore

        @Singleton
        @Provides
        @ConfigurationDataStore
        fun provideConfigurationDataStorePreferences(
            @ApplicationContext context: Context
        ): DataStore<Preferences> =
            context.configurationDataStore

        @Singleton
        @Provides
        @ReceptionSettingDataStore
        fun provideReceptionSettingDataStorePreferences(
            @ApplicationContext context: Context
        ): DataStore<Preferences> =
            context.receptionSettingDataStore

        @Singleton
        @Provides
        @ImageSettingDataStore
        fun provideImageSettingDataStorePreferences(
            @ApplicationContext context: Context
        ): DataStore<Preferences> =
            context.imageSettingDataStore
    }
}