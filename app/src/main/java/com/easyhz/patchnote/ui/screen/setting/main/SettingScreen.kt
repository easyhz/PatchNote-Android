package com.easyhz.patchnote.ui.screen.setting.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.helper.web.WebHelper
import com.easyhz.patchnote.core.common.util.collectInSideEffectWithLifecycle
import com.easyhz.patchnote.core.designSystem.component.dialog.BasicDialog
import com.easyhz.patchnote.core.designSystem.component.dialog.InputDialog
import com.easyhz.patchnote.core.designSystem.component.scaffold.PatchNoteScaffold
import com.easyhz.patchnote.core.designSystem.component.textField.BaseTextField
import com.easyhz.patchnote.core.designSystem.component.topbar.TopBar
import com.easyhz.patchnote.core.designSystem.util.dialog.BasicDialogButton
import com.easyhz.patchnote.core.designSystem.util.topbar.TopBarType
import com.easyhz.patchnote.core.model.setting.EtcSettingItem
import com.easyhz.patchnote.core.model.setting.MajorSettingItem
import com.easyhz.patchnote.core.model.setting.TeamSettingItem
import com.easyhz.patchnote.ui.screen.setting.main.contract.SettingIntent
import com.easyhz.patchnote.ui.screen.setting.main.contract.SettingSideEffect
import com.easyhz.patchnote.ui.theme.Bold20
import com.easyhz.patchnote.ui.theme.MainBackground
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Medium14
import com.easyhz.patchnote.ui.theme.Medium16
import com.easyhz.patchnote.ui.theme.Primary
import com.easyhz.patchnote.ui.theme.SemiBold18
import com.easyhz.patchnote.ui.theme.SubBackground
import com.easyhz.patchnote.ui.theme.SubText2

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel(),
    navigateToUp: () -> Unit,
    navigateToDataManagement: () -> Unit,
    navigateToMyPage: () -> Unit,
    navigateToReceptionSetting: () -> Unit,
    navigateToTeamSelection: () -> Unit,
    navigateToTeamInformation: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    PatchNoteScaffold(
        containerColor = SubBackground,
        topBar = {
            TopBar(
                modifier = Modifier.background(MainBackground),
                left = TopBarType.TopBarIconButton(
                    iconId = R.drawable.ic_arrow_leading,
                    iconAlignment = Alignment.CenterStart,
                    tint = MainText,
                    onClick = { viewModel.postIntent(SettingIntent.NavigateToUp) }
                ),
                title = TopBarType.TopBarTitle(
                    stringId = R.string.setting_title
                ),
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(MajorSettingItem.entries) { major ->
                Column(
                    modifier = Modifier.fillMaxWidth().background(MainBackground),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 44.dp)
                            .padding(horizontal = 20.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = stringResource(major.stringResId),
                            style = Bold20,
                            color = MainText
                        )
                    }
                    major.items.forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 44.dp)
                                .clickable(item.enabledClick) { viewModel.postIntent(SettingIntent.ClickSettingItem(item)) }
                                .padding(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = stringResource(item.stringResId),
                                style = Medium16,
                                color = SubText2,
                            )
                            when(item) {
                                TeamSettingItem.TEAM_INFORMATION -> {
                                    Text(
                                        text = uiState.teamName,
                                        style = Medium14,
                                        color = MainText,
                                    )
                                }
                                EtcSettingItem.VERSION -> {
                                    Text(
                                        text = item.getValue() ?: "",
                                        style = Medium14,
                                        color = SubText2,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (uiState.isInputDialogVisible) {
            InputDialog(
                title = stringResource(R.string.dialog_block_title),
                subTitle = stringResource(R.string.dialog_block_subtitle),
                content = {
                    BaseTextField(
                        containerModifier = Modifier.height(40.dp),
                        value = uiState.blockInputDialogText,
                        onValueChange = { viewModel.postIntent(SettingIntent.ChangeBlockText(it)) },
                        title = null,
                        placeholder = stringResource(R.string.dialog_block_placeholder),
                        singleLine = true,
                        isFilled = false,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        keyboardActions = KeyboardActions(onDone = { viewModel.postIntent(SettingIntent.ProposeBlock) })
                    )
                },
                positiveButton = BasicDialogButton(
                    text = stringResource(R.string.dialog_block_positive),
                    style = SemiBold18.copy(color = MainBackground),
                    backgroundColor = Primary,
                    onClick = { viewModel.postIntent(SettingIntent.ProposeBlock) }
                ),
                negativeButton = BasicDialogButton(
                    text = stringResource(R.string.dialog_block_negative),
                    backgroundColor = SubBackground,
                    onClick = { viewModel.postIntent(SettingIntent.HideBlockDialog) }
                )
            )
        }

        if (uiState.isSuccessDialogVisible) {
            BasicDialog(
                title = stringResource(R.string.dialog_block_propose),
                content = null,
                positiveButton = BasicDialogButton(
                    text = stringResource(R.string.dialog_block_positive),
                    style = SemiBold18.copy(color = MainBackground),
                    backgroundColor = Primary,
                    onClick = { viewModel.postIntent(SettingIntent.HideSuccessDialog) }
                ),
                negativeButton = null
            )
        }
    }

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is SettingSideEffect.NavigateToUp -> navigateToUp()
            is SettingSideEffect.NavigateToAbout -> {
                WebHelper().openWebPage(context, sideEffect.url)
            }
            is SettingSideEffect.NavigateToTeamInformation -> navigateToTeamInformation()
            is SettingSideEffect.NavigateToDataManagement -> navigateToDataManagement()
            is SettingSideEffect.NavigateToTeamSelection -> navigateToTeamSelection()
            is SettingSideEffect.NavigateToMyPage -> navigateToMyPage()
            is SettingSideEffect.NavigateToReceptionSetting -> navigateToReceptionSetting()
            is SettingSideEffect.NavigateToSupport -> {
                WebHelper().openWebPage(context, sideEffect.url)
            }
        }
    }
}