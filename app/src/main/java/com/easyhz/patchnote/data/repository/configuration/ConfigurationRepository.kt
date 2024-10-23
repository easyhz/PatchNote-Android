package com.easyhz.patchnote.data.repository.configuration

import com.easyhz.patchnote.core.model.configuration.Configuration

interface ConfigurationRepository {
    suspend fun fetchConfiguration(): Result<Configuration>
    suspend fun getPassword(): Result<String?>
    suspend fun updatePassword(password: String): Result<Unit>
    suspend fun checkEnteredPassword(): Result<Boolean>
    suspend fun updateEnteredPassword(isEntered: Boolean): Result<Unit>
}