package com.easyhz.patchnote.core.common.error

sealed class AppError: Exception() {
    /* 예상하지 못한 에러 */
    data object UnexpectedError : AppError() {
        @JvmStatic
        private fun readResolve(): Any = UnexpectedError
    }

    /* 결과값 반환 X */
    data object NoResultError : AppError() {
        @JvmStatic
        private fun readResolve(): Any = NoResultError
    }

}