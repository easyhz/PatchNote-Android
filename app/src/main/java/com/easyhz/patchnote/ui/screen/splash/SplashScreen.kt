package com.easyhz.patchnote.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.util.collectInSideEffectWithLifecycle
import com.easyhz.patchnote.ui.screen.splash.contract.SplashSideEffect
import com.easyhz.patchnote.ui.theme.MainBackground

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    navigateToOnboarding: () -> Unit,
) {
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

    viewModel.sideEffect.collectInSideEffectWithLifecycle() {sideEffect ->
        when(sideEffect) {
            is SplashSideEffect.NavigateToHome -> { navigateToHome() }
            is SplashSideEffect.NavigateToOnboarding -> { navigateToOnboarding() }
        }
    }
}