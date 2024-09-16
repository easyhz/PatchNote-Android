package com.easyhz.patchnote.core.common.error

import com.google.firebase.firestore.FirebaseFirestoreException.Code

sealed class FireStoreError: AppError() {
    data object NotFoundError: AppError() {
        @JvmStatic
        private fun readResolve(): Any = NotFoundError
    }

    data object PermissionDeniedError: AppError() {
        @JvmStatic
        private fun readResolve(): Any = NotFoundError
    }
}

fun getErrorByCode(code: Code) : AppError =
    when(code) {
        Code.NOT_FOUND -> FireStoreError.NotFoundError
        Code.PERMISSION_DENIED -> FireStoreError.PermissionDeniedError
        else -> AppError.UnexpectedError
    }