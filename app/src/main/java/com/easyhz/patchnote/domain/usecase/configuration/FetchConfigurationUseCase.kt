package com.easyhz.patchnote.domain.usecase.configuration

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.model.configuration.Configuration
import com.easyhz.patchnote.data.repository.configuration.ConfigurationRepository
import javax.inject.Inject

class FetchConfigurationUseCase @Inject constructor(
    private val configurationRepository: ConfigurationRepository
): BaseUseCase<Unit, Configuration>() {
    override suspend fun invoke(param: Unit): Result<Configuration> {
        return configurationRepository.fetchConfiguration()
    }
}