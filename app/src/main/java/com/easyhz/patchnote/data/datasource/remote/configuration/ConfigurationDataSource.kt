package com.easyhz.patchnote.data.datasource.remote.configuration

import com.easyhz.patchnote.data.model.configuration.ConfigurationData

interface ConfigurationDataSource {
    suspend fun fetchConfiguration(): Result<ConfigurationData>
}