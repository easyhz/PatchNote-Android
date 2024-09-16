package com.easyhz.patchnote.core.common.util

import android.util.Log
import com.easyhz.patchnote.core.common.error.AppError
import com.easyhz.patchnote.core.common.error.getErrorByCode
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

const val TAG = "ResponseHandler"

/**
 * collection 에 document 이름을 지정하고 저장하는 함수
 *
 * @param execute document id 를 지정한 set 쿼리문이 들어옴.
 */
internal suspend inline fun setHandler(
    dispatcher: CoroutineDispatcher,
    crossinline execute: () -> Task<Void>
): Result<Unit> = withContext(dispatcher) {
    runCatching {
        execute().await()
    }.fold(
        onSuccess = { Result.success(Unit) },
        onFailure = { e -> handleException(e, "set") }
    )
}

private fun <T> handleException(e: Throwable, tag: String): Result<T> {
    return when(e) {
        is FirebaseFirestoreException -> { handleFireStoreException(e, tag) }
        is AppError.NoResultError -> { handleNoResultException(e, tag) }
        else -> { handleGeneralException(e, tag) }
    }
}

private fun <T> handleFireStoreException(e: FirebaseFirestoreException, tag: String): Result<T> {
    Log.e(TAG, "In $tag handler - FireStore: ${e.message}")
    return Result.failure(getErrorByCode(e.code))
}

private fun <T> handleNoResultException(e: AppError.NoResultError, tag: String): Result<T> {
    Log.e(TAG, "In $tag handler - NoResult: ${e.message}")
    return Result.failure(AppError.NoResultError)
}

private fun <T> handleGeneralException(e: Throwable, tag: String): Result<T> {
    Log.e(TAG, "In $tag handler - Exception: ${e.message}")
    return Result.failure(AppError.UnexpectedError)
}