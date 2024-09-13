package com.easyhz.patchnote

import android.app.Application
import com.easyhz.patchnote.analytics.AnalyticsManager
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PatchNoteApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        AnalyticsManager.init(this)
    }
}