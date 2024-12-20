package com.easyhz.patchnote.ui.screen.setting.main

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.util.collectInSideEffectWithLifecycle
import com.easyhz.patchnote.core.designSystem.component.dialog.BasicDialog
import com.easyhz.patchnote.core.designSystem.component.dialog.InputDialog
import com.easyhz.patchnote.core.designSystem.component.scaffold.PatchNoteScaffold
import com.easyhz.patchnote.core.designSystem.component.textField.BaseTextField
import com.easyhz.patchnote.core.designSystem.component.topbar.TopBar
import com.easyhz.patchnote.core.designSystem.util.dialog.BasicDialogButton
import com.easyhz.patchnote.core.designSystem.util.topbar.TopBarType
import com.easyhz.patchnote.core.model.setting.SettingItem
import com.easyhz.patchnote.ui.screen.setting.main.contract.SettingIntent
import com.easyhz.patchnote.ui.screen.setting.main.contract.SettingSideEffect
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Medium18
import com.easyhz.patchnote.ui.theme.Primary
import com.easyhz.patchnote.ui.theme.SemiBold18
import com.easyhz.patchnote.ui.theme.SubBackground

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel(),
    navigateToUp: () -> Unit,
    navigateToDataManagement: () -> Unit,
    navigateToMyPage: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    PatchNoteScaffold(
        topBar = {
            TopBar(
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
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(SettingItem.entries) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 44.dp)
                        .clickable { viewModel.postIntent(SettingIntent.ClickSettingItem(it)) }
                        .padding(horizontal = 20.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = stringResource(it.stringResId),
                        style = Medium18,
                        color = MainText,
                    )
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
                    style = SemiBold18.copy(color = Color.White),
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
                    style = SemiBold18.copy(color = Color.White),
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
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(sideEffect.url))
                context.startActivity(intent)
            }
            is SettingSideEffect.NavigateToDataManagement -> navigateToDataManagement()
            is SettingSideEffect.NavigateToMyPage -> navigateToMyPage()
        }
    }
}