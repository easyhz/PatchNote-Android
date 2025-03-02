package com.easyhz.patchnote.core.model.app

sealed class AppStep {
    data object Home : AppStep()
    data object Onboarding : AppStep()
    data object Team : AppStep()
    data class Maintenance(val message: String) : AppStep()
    data class Update(val appVersion: String) : AppStep()
}