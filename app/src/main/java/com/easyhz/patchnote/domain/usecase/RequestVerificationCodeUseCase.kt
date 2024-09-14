package com.easyhz.patchnote.domain.usecase

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.data.model.sign.param.RequestVerificationCodeParam
import com.easyhz.patchnote.data.model.sign.response.RequestVerificationCodeResponse
import com.easyhz.patchnote.data.repository.sign.SignRepository
import javax.inject.Inject

class RequestVerificationCodeUseCase @Inject constructor(
    private val signRepository: SignRepository
): BaseUseCase<RequestVerificationCodeParam, RequestVerificationCodeResponse>() {
    override suspend fun invoke(param: RequestVerificationCodeParam): Result<RequestVerificationCodeResponse> {
        return signRepository.requestVerificationCode(param)
    }
}