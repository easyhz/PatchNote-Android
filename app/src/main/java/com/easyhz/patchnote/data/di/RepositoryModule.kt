package com.easyhz.patchnote.data.di

import com.easyhz.patchnote.data.repository.sign.SignRepository
import com.easyhz.patchnote.data.repository.sign.SignRepositoryImpl
import com.easyhz.patchnote.data.repository.user.UserRepository
import com.easyhz.patchnote.data.repository.user.UserRepositoryImpl
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

    @Binds
    fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository
}