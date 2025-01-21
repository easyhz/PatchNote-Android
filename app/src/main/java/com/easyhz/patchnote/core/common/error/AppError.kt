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

    /* 인덱스 에러 */
    data object IndexError : AppError() {
        @JvmStatic
        private fun readResolve(): Any = IndexError
    }

    /* 유저 데이터 없음 에러 */
    data object NoUserDataError : AppError() {
        @JvmStatic
        private fun readResolve(): Any = NoUserDataError
    }

}