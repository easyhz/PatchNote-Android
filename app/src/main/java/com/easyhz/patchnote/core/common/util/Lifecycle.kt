package com.easyhz.patchnote.core.common.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.compose.LocalLifecycleOwner


@Composable
fun LifecycleEffect(
    onCreate: () -> Unit = { },
    onStart: () -> Unit = { },
    onResume: () -> Unit = { },
    onPause: () -> Unit = { },
    onStop: () -> Unit = { },
    onDestroy: () -> Unit = { },
    onAny: () -> Unit = { }
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Event.ON_CREATE -> onCreate()
                Event.ON_START -> onStart()
                Event.ON_RESUME -> onResume()
                Event.ON_PAUSE -> onPause()
                Event.ON_STOP -> onStop()
                Event.ON_DESTROY -> onDestroy()
                Event.ON_ANY -> onAny()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}