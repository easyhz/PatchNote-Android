package com.easyhz.patchnote.data.di

import com.easyhz.patchnote.data.datasource.auth.AuthDataSource
import com.easyhz.patchnote.data.datasource.auth.AuthDataSourceImpl
import com.easyhz.patchnote.data.datasource.category.CategoryDataSource
import com.easyhz.patchnote.data.datasource.category.CategoryDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    fun bindAuthDataSource(
        authDataSourceImpl: AuthDataSourceImpl
    ): AuthDataSource

    @Binds
    fun bindCategoryDataSource(
        categoryDataSourceImpl: CategoryDataSourceImpl
    ): CategoryDataSource
}