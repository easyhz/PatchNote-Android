package com.easyhz.patchnote.domain.usecase.sign

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.data.model.sign.param.SignInWithPhoneParam
import com.easyhz.patchnote.data.repository.sign.SignRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInWithPhoneUseCase @Inject constructor(
    @Dispatcher(dispatcher = PatchNoteDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val signRepository: SignRepository
) : BaseUseCase<SignInWithPhoneParam, String>() {
    override suspend fun invoke(param: SignInWithPhoneParam): Result<String> = withContext(dispatcher) {
        return@withContext runCatching {
            val credential = signRepository.getCredentials(verificationId = param.verificationId, code = param.code).getOrThrow()
            val authResult = signRepository.signInWithPhone(credential).getOrThrow()
            authResult.user?.uid ?: throw Exception("User not found")
        }
    }
}