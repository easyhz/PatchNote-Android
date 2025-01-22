package com.easyhz.patchnote.core.designSystem.component.dialog

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.designSystem.component.button.MainButton
import com.easyhz.patchnote.core.designSystem.util.extension.dropShadow
import com.easyhz.patchnote.core.designSystem.util.extension.noRippleClickable
import com.easyhz.patchnote.core.model.onboarding.OnboardingStep
import com.easyhz.patchnote.ui.theme.MainBackground
import com.easyhz.patchnote.ui.theme.Medium16
import com.easyhz.patchnote.ui.theme.Primary
import com.easyhz.patchnote.ui.theme.SubBackground
import kotlinx.coroutines.launch

@Composable
fun OnboardingDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit
) {
    val pagerState = rememberPagerState {
        OnboardingStep.entries.size
    }
    val coroutineScope = rememberCoroutineScope()

    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                modifier = Modifier.noRippleClickable {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                },
                painter = painterResource(id = R.drawable.ic_arrow_left),
                tint = MainBackground,
                isVisible = pagerState.canScrollBackward
            )
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(top = 52.dp)
                            .height(28.dp)
                            .align(Alignment.End)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onDismissRequest() }
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Text(
                            text = stringResource(id = R.string.onboarding_dialog_skip),
                            color = MainBackground,
                            style = Medium16.copy(
                                textDecoration = TextDecoration.Underline
                            ),
                        )
                    }
                    HorizontalPager(
                        modifier = Modifier.clip(RoundedCornerShape(16.dp)),
                        state = pagerState,
                    ) {
                        Image(
                            painter = painterResource(id = OnboardingStep.entries[it].imageId),
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth
                        )
                        IconButton(
                            painter = painterResource(id = R.drawable.ic_arrow_right),
                            tint = MainBackground,
                            isVisible = pagerState.canScrollForward
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(OnboardingStep.entries.size) { iteration ->
                        val color =
                            if (pagerState.currentPage == iteration) Primary else SubBackground
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .padding(vertical = 2.dp)
                                .dropShadow(
                                    shape = CircleShape,
                                    blur = 10.dp,
                                )
                                .clip(CircleShape)
                                .background(color)
                                .size(8.dp),
                        )
                    }
                }

                AnimatedContent(
                    targetState = pagerState.currentPage == OnboardingStep.entries.size - 1,
                    label = "",
                    transitionSpec = { fadeIn() togetherWith fadeOut() }
                ) {
                    if (it) {
                        MainButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 2.dp)
                                .dropShadow(
                                    shape = CircleShape,
                                    blur = 10.dp,
                                ),
                            text = stringResource(R.string.onboarding_dialog_button),
                            height = 48.dp,
                        ) {
                            onDismissRequest()
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth()
                        )
                    }
                }
            }
            IconButton(
                modifier = Modifier.noRippleClickable {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                painter = painterResource(id = R.drawable.ic_arrow_right),
                tint = MainBackground,
                isVisible = pagerState.canScrollForward
            )
        }
    }
}

@Preview(
    showBackground = true, backgroundColor = 0xFFFFFFFF
)
@Composable
private fun OnboardingDialogPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        OnboardingDialog {

        }
    }

}