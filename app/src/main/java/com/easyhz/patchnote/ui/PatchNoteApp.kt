package com.easyhz.patchnote.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.easyhz.patchnote.ui.navigation.onboarding.Onboarding
import com.easyhz.patchnote.ui.navigation.onboarding.onboardingNavigation

@Composable
fun PatchNoteApp() {
    val navController = rememberNavController()
    Scaffold { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Onboarding,
        ) {
            onboardingNavigation(navController)
        }
    }
}