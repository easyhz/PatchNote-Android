package com.easyhz.patchnote.core.common.base

abstract class BaseUseCase<P, R> {
    abstract suspend operator fun invoke(param: P): Result<R>
}