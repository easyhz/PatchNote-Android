package com.easyhz.patchnote.data.repository.configuration

import com.easyhz.patchnote.core.model.configuration.Configuration

interface ConfigurationRepository {
    suspend fun fetchConfiguration(): Result<Configuration>
}