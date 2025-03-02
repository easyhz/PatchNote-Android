package com.easyhz.patchnote.ui.screen.splash

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.util.collectInSideEffectWithLifecycle
import com.easyhz.patchnote.core.designSystem.component.dialog.BasicDialog
import com.easyhz.patchnote.ui.screen.splash.contract.SplashSideEffect
import com.easyhz.patchnote.ui.theme.MainBackground

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    navigateToOnboarding: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MainBackground,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(72.dp),
                painter = painterResource(
                    id = R.drawable.app_icon
                ),
                contentDescription = "logo",
            )
        }
    }


    uiState.dialogMessage?.let { dialog ->
        BasicDialog(
            title = dialog.title,
            content = dialog.message,
            positiveButton = dialog.positiveButton,
            negativeButton = dialog.negativeButton
        )
    }

    viewModel.sideEffect.collectInSideEffectWithLifecycle() {sideEffect ->
        when(sideEffect) {
            is SplashSideEffect.NavigateToHome -> { navigateToHome() }
            is SplashSideEffect.NavigateToOnboarding -> { navigateToOnboarding() }
            is SplashSideEffect.NavigateUp -> { (context as Activity).finish() }
            is SplashSideEffect.NavigateToUrl -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(sideEffect.url))
                context.startActivity(intent)
            }
        }
    }
}