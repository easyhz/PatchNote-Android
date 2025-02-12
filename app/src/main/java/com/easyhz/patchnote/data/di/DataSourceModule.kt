package com.easyhz.patchnote.data.di

import com.easyhz.patchnote.data.datasource.local.configuration.ConfigurationLocalDataSource
import com.easyhz.patchnote.data.datasource.local.configuration.ConfigurationLocalDataSourceImpl
import com.easyhz.patchnote.data.datasource.local.defect.DefectLocalDataSource
import com.easyhz.patchnote.data.datasource.local.defect.DefectLocalDataSourceImpl
import com.easyhz.patchnote.data.datasource.local.reception.ReceptionLocalDataSource
import com.easyhz.patchnote.data.datasource.local.reception.ReceptionLocalDataSourceImpl
import com.easyhz.patchnote.data.datasource.local.user.UserLocalDataSource
import com.easyhz.patchnote.data.datasource.local.user.UserLocalDataSourceImpl
import com.easyhz.patchnote.data.datasource.remote.auth.AuthDataSource
import com.easyhz.patchnote.data.datasource.remote.auth.AuthDataSourceImpl
import com.easyhz.patchnote.data.datasource.remote.category.CategoryDataSource
import com.easyhz.patchnote.data.datasource.remote.category.CategoryDataSourceImpl
import com.easyhz.patchnote.data.datasource.remote.configuration.ConfigurationDataSource
import com.easyhz.patchnote.data.datasource.remote.configuration.ConfigurationDataSourceImpl
import com.easyhz.patchnote.data.datasource.remote.defect.DefectDataSource
import com.easyhz.patchnote.data.datasource.remote.defect.DefectDataSourceImpl
import com.easyhz.patchnote.data.datasource.remote.image.ImageDataSource
import com.easyhz.patchnote.data.datasource.remote.image.ImageDataSourceImpl
import com.easyhz.patchnote.data.datasource.remote.team.TeamRemoteDataSourceImpl
import com.easyhz.patchnote.data.datasource.remote.team.TeamRemoteDateSource
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

    @Binds
    fun bindConfigurationDataSource(
        configurationDataSourceImpl: ConfigurationDataSourceImpl
    ): ConfigurationDataSource

    @Binds
    fun bindConfigurationLocalDataSource(
        configurationLocalDataSourceImpl: ConfigurationLocalDataSourceImpl
    ): ConfigurationLocalDataSource

    @Binds
    fun bindTeamRemoteDataSource(
        teamRemoteDataSourceImpl: TeamRemoteDataSourceImpl
    ): TeamRemoteDateSource

    @Binds
    fun bindReceptionLocalDataSource(
        receptionLocalDataSourceImpl: ReceptionLocalDataSourceImpl
    ): ReceptionLocalDataSource

    @Binds
    fun bindDefectLocalDataSource(
        defectLocalDataSourceImpl: DefectLocalDataSourceImpl
    ): DefectLocalDataSource
}