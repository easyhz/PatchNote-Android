package com.easyhz.patchnote.core.common.error

import androidx.annotation.StringRes
import com.easyhz.patchnote.R

@StringRes
fun Throwable.handleError(): Int =
    when (this) {
        AppError.UnexpectedError -> R.string.unexpected_error
        AppError.NoResultError -> R.string.no_result_error
        FireStoreError.NotFoundError -> R.string.firestore_not_found_error
        FireStoreError.PermissionDeniedError -> R.string.firestore_permission_denied_error
        else -> R.string.unexpected_error
    }