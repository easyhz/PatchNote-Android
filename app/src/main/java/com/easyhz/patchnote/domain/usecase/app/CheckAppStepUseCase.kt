package com.easyhz.patchnote.domain.usecase.app

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.core.common.util.version.Version
import com.easyhz.patchnote.core.model.app.AppStep
import com.easyhz.patchnote.core.model.configuration.Configuration
import com.easyhz.patchnote.core.model.user.User
import com.easyhz.patchnote.data.repository.configuration.ConfigurationRepository
import com.easyhz.patchnote.data.repository.user.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CheckAppStepUseCase @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository,
    private val configurationRepository: ConfigurationRepository
): BaseUseCase<Unit, AppStep>() {

    override suspend fun invoke(param: Unit): Result<AppStep> = runCatching {
        val fetchResult = fetchResults()

        determineAppStep(fetchResult)
            .also { userRepository.updateUser(fetchResult.user) }
    }

    /**
     * 비동기 처리 분리
     *
     * - 로그인 여부
     * - 사용자 정보
     * - 앱 설정 정보
     * */
    private suspend fun fetchResults(): FetchResult = withContext(dispatcher) {
        awaitAll(
            async { userRepository.isLogin() },
            async { userRepository.getUserFromLocal().getOrThrow() },
            async { configurationRepository.fetchConfiguration().getOrThrow() },
        ).let {
            FetchResult(
                isLogin = it[0] as Boolean,
                user = it[1] as User,
                configuration = it[2] as Configuration
            )
        }
    }

    /** ✅ 앱 상태 결정 로직 분리 */
    private fun determineAppStep(fetchResult: FetchResult): AppStep = with(fetchResult) {
        when {
            Version.needsUpdate(configuration.androidVersion) -> AppStep.Update(configuration.androidVersion)
            configuration.maintenanceNotice.isNotBlank() -> AppStep.Maintenance(configuration.maintenanceNotice)
            isLogin && user.id.isNotBlank() && user.teamId.isNotBlank() -> AppStep.Home
            else -> AppStep.Onboarding
        }
    }
}

private data class FetchResult(
    val isLogin: Boolean,
    val user: User,
    val configuration: Configuration
)