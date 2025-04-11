package com.easyhz.patchnote.ui.screen.setting.reception

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.util.collectInSideEffectWithLifecycle
import com.easyhz.patchnote.core.designSystem.component.scaffold.PatchNoteScaffold
import com.easyhz.patchnote.core.designSystem.component.toggle.SettingToggle
import com.easyhz.patchnote.core.designSystem.component.topbar.TopBar
import com.easyhz.patchnote.core.designSystem.util.topbar.TopBarType
import com.easyhz.patchnote.ui.screen.setting.reception.contract.ReceptionSettingIntent
import com.easyhz.patchnote.ui.screen.setting.reception.contract.ReceptionSettingSideEffect
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Regular16
import com.easyhz.patchnote.ui.theme.SubText


@Composable
fun ReceptionSettingScreen(
    modifier: Modifier = Modifier,
    viewModel: ReceptionSettingViewModel = hiltViewModel(),
    navigateToUp: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PatchNoteScaffold(
        topBar = {
            TopBar(
                left = TopBarType.TopBarIconButton(
                    iconId = R.drawable.ic_arrow_leading,
                    iconAlignment = Alignment.CenterStart,
                    tint = MainText,
                    onClick = { viewModel.postIntent(ReceptionSettingIntent.NavigateToUp) }
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
                    title = stringResource(type.nameId),
                    isChecked = isChecked,
                    onCheckedChange = { newValue ->
                        viewModel.postIntent(ReceptionSettingIntent.ClickToggleButton(type, newValue))
                    }
                )
            }
            item {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = stringResource(R.string.setting_reception_settings_description),
                    color = SubText,
                    style = Regular16
                )
            }
        }
    }

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is ReceptionSettingSideEffect.NavigateToUp -> navigateToUp()
        }
    }
}