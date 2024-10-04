package com.easyhz.patchnote.core.designSystem.component.loading

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.designSystem.util.extension.noRippleClickable
import com.easyhz.patchnote.ui.theme.MainBackground

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    val focusManager = LocalFocusManager.current
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isLoading
    )
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    LaunchedEffect(isLoading) {
        focusManager.clearFocus()
    }
    AnimatedVisibility(
        visible = isLoading
    ) {
        Box(
            modifier = modifier.fillMaxSize().noRippleClickable {  }.background(MainBackground.copy(0.5f)),
        ) {
            LottieAnimation(
                modifier = Modifier
                    .size((screenWidthDp / 4).dp)
                    .align(Alignment.Center),
                composition = composition,
                progress = { progress },
            )
        }
    }
}

@Preview
@Composable
private fun LoadingIndicatorPreview() {
    LoadingIndicator(isLoading = true)

}