package com.easyhz.patchnote.core.common.error

import androidx.annotation.StringRes

@StringRes
fun Throwable.getMessageStringRes(): Int = 0
//    when (this) {
//        AppError.UnexpectedError -> R.string.unexpected_error
//        AppError.NoResultError -> R.string.no_result_error
//        FireStoreError.NotFoundError -> R.string.firestore_not_found_error
//        FireStoreError.PermissionDeniedError -> R.string.firestore_permission_denied_error
//        else -> R.string.unexpected_error
//    }