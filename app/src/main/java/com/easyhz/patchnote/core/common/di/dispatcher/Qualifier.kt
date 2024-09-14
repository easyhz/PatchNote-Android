package com.easyhz.patchnote.core.common.di.dispatcher

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: PatchNoteDispatchers)

enum class PatchNoteDispatchers {
    DEFAULT,
    IO,
}