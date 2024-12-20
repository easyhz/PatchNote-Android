package com.easyhz.patchnote.core.common.di.dispatcher

import com.easyhz.patchnote.core.common.util.CrashlyticsLogger
import com.easyhz.patchnote.core.common.util.CrashlyticsLoggerImpl
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {
    @Provides
    fun provideCrashlytics(): FirebaseCrashlytics {
        return FirebaseCrashlytics.getInstance()
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface CommonBindModule {
    @Binds
    fun bindCrashlyticsLogger(
        crashlyticsLoggerImpl: CrashlyticsLoggerImpl
    ): CrashlyticsLogger
}