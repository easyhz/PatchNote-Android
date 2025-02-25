package com.easyhz.patchnote.ui.screen.splash

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.util.collectInSideEffectWithLifecycle
import com.easyhz.patchnote.core.designSystem.component.dialog.BasicDialog
import com.easyhz.patchnote.core.designSystem.util.dialog.BasicDialogButton
import com.easyhz.patchnote.ui.screen.splash.contract.SplashIntent
import com.easyhz.patchnote.ui.screen.splash.contract.SplashSideEffect
import com.easyhz.patchnote.ui.theme.MainBackground
import com.easyhz.patchnote.ui.theme.Primary
import com.easyhz.patchnote.ui.theme.SemiBold18

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
                modifier = Modifier.align(Alignment.Center).size(72.dp),
                painter = painterResource(
                    id = R.drawable.app_icon
                ),
                contentDescription = "logo",
            )
        }
    }

    if (uiState.needsUpdate) {
        BasicDialog(
            title = stringResource(R.string.version_dialog_title),
            content = null,
            positiveButton = BasicDialogButton(
                text = stringResource(R.string.version_dialog_button),
                style = SemiBold18.copy(color = MainBackground),
                backgroundColor = Primary,
                onClick = { viewModel.postIntent(SplashIntent.UpdateAppVersion) }
            ),
            negativeButton = null
        )
    }

    viewModel.sideEffect.collectInSideEffectWithLifecycle() {sideEffect ->
        when(sideEffect) {
            is SplashSideEffect.NavigateToHome -> { navigateToHome() }
            is SplashSideEffect.NavigateToOnboarding -> { navigateToOnboarding() }
            is SplashSideEffect.NavigateToUrl -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(sideEffect.url))
                context.startActivity(intent)
            }
        }
    }
}