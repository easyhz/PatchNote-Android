package com.easyhz.patchnote.core.common.util

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * SideEffect Collect 함수
 */
@SuppressLint("ComposableNaming")
@Composable
fun <T> Flow<T>.collectInSideEffectWithLifecycle(
    vararg keys: Any?,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    collector: suspend CoroutineScope.(sideEffect: T) -> Unit,
) {
    val currentCollector by rememberUpdatedState(collector)

    LaunchedEffect(this, lifecycle, minActiveState, *keys) {
        withContext(Dispatchers.Main.immediate) {
            lifecycle.repeatOnLifecycle(minActiveState) {
                this@collectInSideEffectWithLifecycle.collect { currentCollector(it) }
            }
        }
    }
}