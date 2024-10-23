package com.easyhz.patchnote.data.repository.configuration

import com.easyhz.patchnote.core.model.configuration.Configuration
import com.easyhz.patchnote.data.datasource.local.configuration.ConfigurationLocalDataSource
import com.easyhz.patchnote.data.mapper.configuration.toModel
import javax.inject.Inject

class ConfigurationRepositoryImpl @Inject constructor(
    private val configurationDataSource: ConfigurationDataSource
): ConfigurationRepository {
    override suspend fun fetchConfiguration(): Result<Configuration> {
        return configurationDataSource.fetchConfiguration().map { it.toModel() }
    }
}