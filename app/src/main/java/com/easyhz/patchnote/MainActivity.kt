package com.easyhz.patchnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.easyhz.patchnote.ui.PatchNoteApp
import com.easyhz.patchnote.ui.theme.PatchNoteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PatchNoteTheme {
                PatchNoteApp()
            }
        }
    }
}