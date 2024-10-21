package com.easyhz.patchnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easyhz.patchnote.ui.PatchNoteApp
import com.easyhz.patchnote.ui.main.MainViewModel
import com.easyhz.patchnote.ui.theme.PatchNoteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition {
                uiState.isLoading
            }
            PatchNoteTheme {
                PatchNoteApp(uiState.isLogin)
            }
        }
    }
}