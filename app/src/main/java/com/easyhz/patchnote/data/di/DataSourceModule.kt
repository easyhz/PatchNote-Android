package com.easyhz.patchnote.data.di

import com.easyhz.patchnote.data.datasource.auth.AuthDataSource
import com.easyhz.patchnote.data.datasource.auth.AuthDataSourceImpl
import com.easyhz.patchnote.data.datasource.category.CategoryDataSource
import com.easyhz.patchnote.data.datasource.category.CategoryDataSourceImpl
import com.easyhz.patchnote.data.datasource.defect.DefectDataSource
import com.easyhz.patchnote.data.datasource.defect.DefectDataSourceImpl
import com.easyhz.patchnote.data.datasource.image.ImageDataSource
import com.easyhz.patchnote.data.datasource.image.ImageDataSourceImpl
import com.easyhz.patchnote.data.datasource.user.UserLocalDataSource
import com.easyhz.patchnote.data.datasource.user.UserLocalDataSourceImpl
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

    @Binds
    fun bindDefectDataSource(
        defectDataSourceImpl: DefectDataSourceImpl
    ): DefectDataSource

    @Binds
    fun bindImageDataSource(
        imageDataSourceImpl: ImageDataSourceImpl
    ): ImageDataSource

    @Binds
    fun bindUserLocalDataSource(
        userDataSourceImpl: UserLocalDataSourceImpl
    ): UserLocalDataSource
}