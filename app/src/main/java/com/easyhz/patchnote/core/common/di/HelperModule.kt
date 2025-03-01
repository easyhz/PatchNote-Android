package com.easyhz.patchnote.core.common.di

import com.easyhz.patchnote.core.common.util.log.AppLogger
import com.easyhz.patchnote.core.common.util.log.Logger
import com.easyhz.patchnote.core.common.util.resource.DefaultResourceHelper
import com.easyhz.patchnote.core.common.util.resource.ResourceHelper
import com.easyhz.patchnote.core.common.util.serializable.MoshiSerializableHelper
import com.easyhz.patchnote.core.common.util.serializable.SerializableHelper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface HelperModule {

    @Binds
    @Singleton
    fun bindLogger(
        appLogger: AppLogger
    ): Logger

    @Binds
    @Singleton
    fun bindSerializableHelper(
        moshiSerializableHelper: MoshiSerializableHelper
    ): SerializableHelper

    @Binds
    @Singleton
    fun bindResourceHelper(
        defaultResourceHelper: DefaultResourceHelper
    ): ResourceHelper
}