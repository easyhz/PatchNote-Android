package com.easyhz.patchnote.data.repository.sign

import com.easyhz.patchnote.data.model.sign.param.RequestVerificationCodeParam
import com.easyhz.patchnote.data.model.sign.response.RequestVerificationCodeResponse


interface SignRepository {
    suspend fun requestVerificationCode(param: RequestVerificationCodeParam): Result<RequestVerificationCodeResponse>
}