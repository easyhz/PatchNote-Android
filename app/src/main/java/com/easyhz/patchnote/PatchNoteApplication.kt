package com.easyhz.patchnote

import android.app.Application
import com.easyhz.patchnote.core.analytics.AnalyticsManager
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.ktx.appCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.ktx.Firebase
import com.microsoft.clarity.Clarity
import com.microsoft.clarity.ClarityConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PatchNoteApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        AnalyticsManager.init(this)
        Firebase.appCheck.installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance()
        )
        val config = ClarityConfig(BuildConfig.CLARITY_PROJECT_ID)
        Clarity.initialize(applicationContext, config)
    }
}