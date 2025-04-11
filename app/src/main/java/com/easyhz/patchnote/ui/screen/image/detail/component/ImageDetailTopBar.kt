package com.easyhz.patchnote.ui.screen.image.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.designSystem.component.button.CheckBoxButton
import com.easyhz.patchnote.core.designSystem.component.topbar.TopBarIconButton
import com.easyhz.patchnote.core.designSystem.util.extension.noRippleClickable
import com.easyhz.patchnote.ui.theme.MainBackground
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Medium18
import com.easyhz.patchnote.ui.theme.SemiBold20

@Composable
fun ImageDetailTopBar(
    modifier: Modifier = Modifier,
    title: String,
    checked: Boolean,
    navigateUp: () -> Unit,
    onClickDisplayButton: (Boolean) -> Unit,
) {
    Box(
        modifier = modifier
            .height(52.dp)
            .fillMaxWidth()
            .background(color = MainText.copy(alpha = 0.5f))
            .padding(horizontal = 18.dp)
    ) {
        TopBarIconButton(
            modifier = Modifier.padding(start = 2.dp).align(Alignment.CenterStart),
            painter = painterResource(R.drawable.ic_close_leading),
            alignment = Alignment.CenterStart,
            tint = MainBackground,
            onClick = navigateUp
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = title,
            style = SemiBold20,
            color = MainBackground
        )
        DisplayButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            checked = checked,
            onClick = onClickDisplayButton
        )
    }
}

@Composable
private fun DisplayButton(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onClick: (Boolean) -> Unit
) {
    Row(
        modifier = modifier.heightIn(min = 32.dp).noRippleClickable { onClick(!checked) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        CheckBoxButton(
            checked = checked,
            onCheckedChange = onClick
        )
        Text(
            text = stringResource(R.string.image_detail_defect_information),
            style = Medium18.copy(
                color = MainBackground
            )
        )
    }
}

@Preview
@Composable
private fun ImageDetailTopBarPreview() {
    ImageDetailTopBar(
        title = "1/3",
        checked = true,
        navigateUp = {},
        onClickDisplayButton = {}
    )
}

@Preview
@Composable
private fun DisplayButtonPreview() {
    DisplayButton(
        checked = false,
        onClick = {}
    )

}