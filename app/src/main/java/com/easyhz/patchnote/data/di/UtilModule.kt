package com.easyhz.patchnote.data.di

import com.easyhz.patchnote.BuildConfig
import com.easyhz.patchnote.data.util.AesEncryption
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {
    @Provides
    fun provideEncryption(): AesEncryption {
        return AesEncryption(BuildConfig.AES_ENCRYPTION_KEY)
    }
}