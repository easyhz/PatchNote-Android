package com.easyhz.patchnote.core.designSystem.util.topbar

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.easyhz.patchnote.core.designSystem.component.topbar.TopBarIconButton
import com.easyhz.patchnote.core.designSystem.component.topbar.TopBarTextButton
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.SemiBold20

sealed class TopBarType {
    data class TopBarTitle(
        @StringRes val stringId: Int
    ): TopBarType()

    data class TopBarTextButton(
        @StringRes val stringId: Int,
        val textColor: Color,
        val textAlignment: Alignment,
        val onClick: () -> Unit
    ) : TopBarType()

    data class TopBarIconButton(
        @DrawableRes val iconId: Int,
        val iconAlignment: Alignment,
        val tint: Color,
        val onClick: () -> Unit
    ) : TopBarType()
}

@SuppressLint("ComposableNaming")
@Composable
fun TopBarType?.content(
    modifier: Modifier = Modifier
) {
    when(this) {
        is TopBarType.TopBarTextButton -> {
            TopBarTextButton(
                modifier = modifier,
                text = stringResource(id = this.stringId),
                textColor = this.textColor,
                alignment = this.textAlignment,
                onClick = this.onClick
            )
        }
        is TopBarType.TopBarIconButton -> {
            TopBarIconButton(
                modifier = modifier,
                painter = painterResource(this.iconId),
                alignment = this.iconAlignment,
                tint = this.tint,
                onClick = this.onClick
            )
        }
        is TopBarType.TopBarTitle -> {
            Text(
                modifier = modifier,
                text = stringResource(id = this.stringId),
                style = SemiBold20,
                color = MainText
            )
        }
        null -> { }
    }
}