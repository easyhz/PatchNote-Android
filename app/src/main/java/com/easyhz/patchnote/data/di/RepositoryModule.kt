package com.easyhz.patchnote.data.di

import com.easyhz.patchnote.data.repository.category.CategoryRepository
import com.easyhz.patchnote.data.repository.category.CategoryRepositoryImpl
import com.easyhz.patchnote.data.repository.configuration.ConfigurationRepository
import com.easyhz.patchnote.data.repository.configuration.ConfigurationRepositoryImpl
import com.easyhz.patchnote.data.repository.defect.DefectRepository
import com.easyhz.patchnote.data.repository.defect.DefectRepositoryImpl
import com.easyhz.patchnote.data.repository.image.ImageRepository
import com.easyhz.patchnote.data.repository.image.ImageRepositoryImpl
import com.easyhz.patchnote.data.repository.sign.SignRepository
import com.easyhz.patchnote.data.repository.sign.SignRepositoryImpl
import com.easyhz.patchnote.data.repository.team.TeamRepository
import com.easyhz.patchnote.data.repository.team.TeamRepositoryImpl
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

    @Binds
    fun bindCategoryRepository(
        categoryRepositoryImpl: CategoryRepositoryImpl
    ): CategoryRepository

    @Binds
    fun bindImageRepository(
        imageRepositoryImpl: ImageRepositoryImpl
    ): ImageRepository

    @Binds
    fun bindDefectRepository(
        defectRepositoryImpl: DefectRepositoryImpl
    ): DefectRepository

    @Binds
    fun bindConfigurationRepository(
        configurationRepositoryImpl: ConfigurationRepositoryImpl
    ): ConfigurationRepository

    @Binds
    fun bindTeamRepository(
        teamRepositoryImpl: TeamRepositoryImpl
    ): TeamRepository
}