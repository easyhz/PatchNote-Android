package com.easyhz.patchnote.core.supabase.di

import com.easyhz.patchnote.core.supabase.service.auth.AuthService
import com.easyhz.patchnote.core.supabase.service.auth.AuthServiceImpl
import com.easyhz.patchnote.core.supabase.service.user.UserService
import com.easyhz.patchnote.core.supabase.service.user.UserServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ServiceModule {

    @Binds
    fun bindAuthService(
        authServiceImpl: AuthServiceImpl
    ): AuthService

    @Binds
    fun bindUserService(
        userServiceImpl: UserServiceImpl
    ): UserService

}