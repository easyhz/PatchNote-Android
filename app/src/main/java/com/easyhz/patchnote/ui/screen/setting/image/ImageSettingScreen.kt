package com.easyhz.patchnote.ui.screen.setting.image

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.util.collectInSideEffectWithLifecycle
import com.easyhz.patchnote.core.designSystem.component.scaffold.PatchNoteScaffold
import com.easyhz.patchnote.core.designSystem.component.toggle.SettingToggle
import com.easyhz.patchnote.core.designSystem.component.topbar.TopBar
import com.easyhz.patchnote.core.designSystem.util.topbar.TopBarType
import com.easyhz.patchnote.core.model.image.DisplayImageType
import com.easyhz.patchnote.ui.screen.setting.image.contract.ImageSettingIntent
import com.easyhz.patchnote.ui.screen.setting.image.contract.ImageSettingSideEffect
import com.easyhz.patchnote.ui.screen.setting.image.contract.ImageSettingState
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Regular16
import com.easyhz.patchnote.ui.theme.SubText

/**
 * Date: 2025. 4. 11.
 * Time: 오후 10:12
 */

@Composable
fun ImageSettingScreen(
    modifier: Modifier = Modifier,
    viewModel: ImageSettingViewModel = hiltViewModel(),
    navigateUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ImageSettingScreen(
        modifier = modifier,
        uiState = uiState,
        navigateUp = { viewModel.postIntent(ImageSettingIntent.NavigateUp) },
        onCheckedChange = { type, value -> viewModel.postIntent(ImageSettingIntent.ClickToggleButton(displayImageType = type, newValue = value)) }
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is ImageSettingSideEffect.NavigateUp -> navigateUp()
        }
    }
}

@Composable
private fun ImageSettingScreen(
    modifier: Modifier = Modifier,
    uiState: ImageSettingState,
    navigateUp: () -> Unit,
    onCheckedChange: (DisplayImageType, Boolean) -> Unit
) {
    PatchNoteScaffold(
        topBar = {
            TopBar(
                left = TopBarType.TopBarIconButton(
                    iconId = R.drawable.ic_arrow_leading,
                    iconAlignment = Alignment.CenterStart,
                    tint = MainText,
                    onClick = navigateUp
                ),
                title = TopBarType.TopBarTitle(
                    stringId = R.string.setting_reception_settings
                ),
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier.padding(innerPadding).padding(horizontal = 20.dp),
            contentPadding = PaddingValues(vertical = 8.dp),
        ) {
            items(uiState.items.toList()) { (type, isChecked) ->
                SettingToggle(
                    title = stringResource(type.displayNameId),
                    isChecked = isChecked,
                    onCheckedChange = {
                        onCheckedChange(type, it)
                    },
                )
            }
            item {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = stringResource(R.string.setting_image_settings_description),
                    color = SubText,
                    style = Regular16
                )
            }
        }
    }
}

@Preview
@Composable
private fun ImageSettingScreenPreview() {
    ImageSettingScreen(
        uiState = ImageSettingState.init(),
        navigateUp = {},
        onCheckedChange = { _, _ -> }
    )
}