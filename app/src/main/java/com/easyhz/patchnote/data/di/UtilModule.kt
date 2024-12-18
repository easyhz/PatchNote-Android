package com.easyhz.patchnote.data.di

import android.content.Context
import com.easyhz.patchnote.BuildConfig
import com.easyhz.patchnote.data.util.AesEncryption
import com.easyhz.patchnote.data.util.ExportUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {
    @Provides
    fun provideEncryption(): AesEncryption {
        return AesEncryption(BuildConfig.AES_ENCRYPTION_KEY)
    }

    @Provides
    fun provideExportUtil(
        @ApplicationContext context: Context,
    ): ExportUtil{
        return ExportUtil(context)
    }
}