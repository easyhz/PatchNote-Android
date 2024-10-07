package com.easyhz.patchnote.core.designSystem.component.textField

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.core.designSystem.util.textField.TextFieldState
import com.easyhz.patchnote.core.designSystem.util.textField.getTextFieldState
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Medium18
import com.easyhz.patchnote.ui.theme.PlaceholderText
import com.easyhz.patchnote.ui.theme.SemiBold18
import com.easyhz.patchnote.ui.theme.SubBackground

@Composable
internal fun TextFieldContainer(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    title: String?,
    placeholder: String,
    spacing: Dp = 12.dp,
    innerTextField: @Composable () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        title?.let {
            TextFieldContainerTitle(
                modifier = modifier.align(Alignment.Top),
                title = title,
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            TextFieldContainerContent(
                modifier = modifier,
                state = state,
                placeholder = placeholder,
                innerTextField = innerTextField,
            )
        }
    }
}

@Composable
internal fun TextFieldContainerTitle(
    modifier: Modifier = Modifier,
    title: String,
) {
    Box(
        modifier = modifier.widthIn(min = 60.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = title,
            style = SemiBold18,
            color = MainText
        )
    }
}

@Composable
private fun TextFieldContainerContent(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    placeholder: String,
    innerTextField: @Composable () -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth().clip(RoundedCornerShape(4.dp)).background(SubBackground).padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        innerTextField()
        if (state == TextFieldState.Default) {
            Text(
                modifier = Modifier.fillMaxWidth().align(Alignment.CenterStart),
                text = placeholder,
                style = Medium18,
                color = PlaceholderText
            )
        }
    }
}


@Preview
@Composable
private fun TextFieldContainerPrev() {
    val state = getTextFieldState("", false)
    TextFieldContainer(
        modifier = Modifier.height(40.dp),
        state = state, title = null, placeholder = "제목을 입력하세요."
    ) {

    }
}
