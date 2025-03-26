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
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CheckAppStepUseCase @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository,
    private val configurationRepository: ConfigurationRepository
): BaseUseCase<Unit, AppStep>() {

    override suspend fun invoke(param: Unit): Result<AppStep> = runCatching {
        val fetchResult = fetchResults()
        userRepository.updateUserFromRemote().getOrNull()
        determineAppStep(fetchResult)
    }

    /**
     * 비동기 처리 분리
     *
     * - 로그인 여부
     * - 사용자 정보
     * - 앱 설정 정보
     * */
    private suspend fun fetchResults(): FetchResult = withContext(dispatcher) {

        val isLoginDeferred = async { userRepository.isLogin() }
        val userDeferred = async { userRepository.getUserFromLocal().getOrNull() }
        val configurationDeferred = async { configurationRepository.fetchConfiguration().getOrThrow() }

        val isLogin = isLoginDeferred.await()
        val user = userDeferred.await()
        val configuration = configurationDeferred.await()

        FetchResult(
            isLogin = isLogin,
            user = user,
            configuration = configuration
        )
    }

    /** ✅ 앱 상태 결정 로직 분리 */
    private fun determineAppStep(fetchResult: FetchResult): AppStep = with(fetchResult) {
        val requiresUpdate = Version.needsUpdate(configuration.androidVersion)
        val isMaintenance = configuration.maintenanceNotice.isNotBlank()
        val isLoggedIn = isLogin && user?.id?.isNotBlank() == true
        val hasNoTeam = user?.currentTeamId.isNullOrBlank()

        return when {
            requiresUpdate -> AppStep.Update(configuration.androidVersion)
            isMaintenance -> AppStep.Maintenance(configuration.maintenanceNotice)
            isLoggedIn && hasNoTeam -> AppStep.Team
            isLoggedIn && !hasNoTeam -> AppStep.Home
            else -> AppStep.Onboarding
        }
    }

    private data class FetchResult(
        val isLogin: Boolean,
        val user: User?,
        val configuration: Configuration
    )
}

