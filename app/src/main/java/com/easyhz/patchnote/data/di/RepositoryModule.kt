package com.easyhz.patchnote.data.di

import com.easyhz.patchnote.data.repository.sign.SignRepository
import com.easyhz.patchnote.data.repository.sign.SignRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindSignRepository(
        signRepositoryImpl: SignRepositoryImpl
    ): SignRepository
}