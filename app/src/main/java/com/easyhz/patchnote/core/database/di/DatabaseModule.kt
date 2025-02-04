package com.easyhz.patchnote.core.database.di

import android.content.Context
import androidx.room.Room
import com.easyhz.patchnote.core.database.AppDatabase
import com.easyhz.patchnote.core.database.defect.dao.OfflineDefectDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideOfflineDefectDao(appDatabase: AppDatabase): OfflineDefectDao {
        return appDatabase.offlineDefectDao
    }
}