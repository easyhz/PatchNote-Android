package com.easyhz.patchnote.core.designSystem.util.transition

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween

const val DURATION = 500

fun <T> AnimatedContentTransitionScope<T>.enterSlide(direction: SlideDirection) =
    slideIntoContainer(direction.direction, tween(DURATION))

fun <T> AnimatedContentTransitionScope<T>.exitSlide(direction: SlideDirection) =
    slideOutOfContainer(direction.direction, tween(DURATION))


enum class SlideDirection(
    val direction: AnimatedContentTransitionScope.SlideDirection
) {
    Up(AnimatedContentTransitionScope.SlideDirection.Up),
    Down(AnimatedContentTransitionScope.SlideDirection.Down),
    Start(AnimatedContentTransitionScope.SlideDirection.Start),
    End(AnimatedContentTransitionScope.SlideDirection.End),
    Right(AnimatedContentTransitionScope.SlideDirection.Right),
    Left(AnimatedContentTransitionScope.SlideDirection.Left)
}