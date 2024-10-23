package com.easyhz.patchnote.data.repository.configuration

import com.easyhz.patchnote.core.model.configuration.Configuration
import com.easyhz.patchnote.data.datasource.local.configuration.ConfigurationLocalDataSource
import com.easyhz.patchnote.data.datasource.remote.configuration.ConfigurationDataSource
import com.easyhz.patchnote.data.mapper.configuration.toModel
import com.easyhz.patchnote.data.util.AesEncryption
import javax.inject.Inject

class ConfigurationRepositoryImpl @Inject constructor(
    private val configurationDataSource: ConfigurationDataSource,
    private val configurationLocalDataSource: ConfigurationLocalDataSource,
    private val encryption: AesEncryption,
): ConfigurationRepository {
    override suspend fun fetchConfiguration(): Result<Configuration> {
        return configurationDataSource.fetchConfiguration().map { it.toModel() }
    }

    override suspend fun getPassword(): Result<String?> = runCatching {
        val password = configurationLocalDataSource.getPassword().getOrNull()
        encryption.decrypt(password)
    }

    override suspend fun updatePassword(password: String) = runCatching {
        configurationLocalDataSource.updatePassword(encryption.encrypt(password))
    }

    override suspend fun checkEnteredPassword(): Result<Boolean> {
        return configurationLocalDataSource.checkEnteredPassword()
    }

    override suspend fun updateEnteredPassword(isEntered: Boolean): Result<Unit> = runCatching {
        configurationLocalDataSource.updateEnteredPassword(isEntered)
    }
}