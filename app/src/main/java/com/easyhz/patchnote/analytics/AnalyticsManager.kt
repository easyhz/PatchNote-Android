package com.easyhz.patchnote.analytics

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics

object AnalyticsManager {
    lateinit var firebaseAnalytics: FirebaseAnalytics
    fun init(context: Context) {
        firebaseAnalytics = FirebaseAnalytics.getInstance(context)
    }
}