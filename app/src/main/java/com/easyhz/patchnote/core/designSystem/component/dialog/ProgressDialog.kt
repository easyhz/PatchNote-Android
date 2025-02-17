package com.easyhz.patchnote.core.designSystem.component.dialog

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.ProgressIndicatorDefaults.drawStopIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.easyhz.patchnote.ui.theme.MainBackground
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.PlaceholderText
import com.easyhz.patchnote.ui.theme.Primary
import com.easyhz.patchnote.ui.theme.SemiBold16
import com.easyhz.patchnote.ui.theme.SemiBold20
import com.easyhz.patchnote.ui.theme.SubBackground
import kotlinx.coroutines.delay

@Composable
fun ProgressDialog(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    progress: Float,
    onDismissRequest: () -> Unit = {}
) {
    val innerPadding = 20.dp
    val buttonMinWidth = 132.dp
    val buttonSpacing = 12.dp
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        label = ""
    )

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = modifier
                .widthIn(max = innerPadding * 2 + buttonMinWidth * 2 + buttonSpacing + 32.dp)
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MainBackground)
                .padding(innerPadding)
                .padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = SemiBold20,
                color = MainText
            )
            Text(
                modifier = Modifier.padding(bottom = 12.dp),
                text = content,
                textAlign = TextAlign.Center,
                style = SemiBold16,
                color = PlaceholderText
            )

            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = Primary,
                trackColor = SubBackground,
                strokeCap = StrokeCap.Square,
                gapSize = 0.dp
            ) {
                drawStopIndicator(
                    drawScope = this,
                    stopSize = 0.dp,
                    color = Primary,
                    strokeCap = StrokeCap.Square
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProgressDialogPreview() {
    val value = remember { mutableFloatStateOf(0.1f) }
    LaunchedEffect(Unit) {
        for (i in 10 until 110 step 10) {
            delay(1000)
            value.floatValue = i.toFloat() / 100
        }
    }
    ProgressDialog(
        title = "Title",
        content = "Content",
        progress = value.floatValue
    ) {}
}