package com.easyhz.patchnote.ui.screen.setting.team

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.util.collectInSideEffectWithLifecycle
import com.easyhz.patchnote.core.designSystem.component.card.MyPageCard
import com.easyhz.patchnote.core.designSystem.component.dialog.BasicDialog
import com.easyhz.patchnote.core.designSystem.component.loading.LoadingIndicator
import com.easyhz.patchnote.core.designSystem.component.scaffold.PatchNoteScaffold
import com.easyhz.patchnote.core.designSystem.component.topbar.TopBar
import com.easyhz.patchnote.core.designSystem.util.dialog.BasicDialogButton
import com.easyhz.patchnote.core.designSystem.util.topbar.TopBarType
import com.easyhz.patchnote.core.model.setting.TeamItem
import com.easyhz.patchnote.ui.screen.setting.team.contract.TeamInformationIntent
import com.easyhz.patchnote.ui.screen.setting.team.contract.TeamInformationSideEffect
import com.easyhz.patchnote.ui.screen.setting.team.contract.TeamInformationState
import com.easyhz.patchnote.ui.theme.LocalSnackBarHostState
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.SubBackground
import com.easyhz.patchnote.ui.theme.SubText

/**
 * Date: 2025. 3. 26.
 * Time: 오후 2:20
 */

@Composable
fun TeamInformationScreen(
    modifier: Modifier = Modifier,
    viewModel: TeamInformationViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToDataManagement: () -> Unit,
    navigateToMember: () -> Unit,
    navigateToSplash: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val clipboardManager = LocalClipboardManager.current
    val snackBarHostState = LocalSnackBarHostState.current
    val context = LocalContext.current

    TeamInformationScreen(
        modifier = modifier,
        uiState = uiState,
        navigateUp = { viewModel.postIntent(TeamInformationIntent.NavigateUp) },
        onClickMyPageItem = { viewModel.postIntent(TeamInformationIntent.ClickTeamInformationItem(it)) },
        onDialogAction = { viewModel.postIntent(TeamInformationIntent.ShowError(null)) }
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is TeamInformationSideEffect.NavigateUp -> navigateUp()
            is TeamInformationSideEffect.NavigateToDataManagement -> navigateToDataManagement()
            is TeamInformationSideEffect.NavigateToMember -> navigateToMember()
            is TeamInformationSideEffect.NavigateToSplash -> navigateToSplash()
            is TeamInformationSideEffect.CopyTeamInviteCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.inviteCode))
                snackBarHostState.showSnackbar(
                    message = context.getString(R.string.setting_my_page_team_invite_code_copy_success),
                    withDismissAction = true
                )
            }
            is TeamInformationSideEffect.ShowSnackBar -> snackBarHostState.showSnackbar(
                message = sideEffect.value,
                withDismissAction = true
            )
        }
    }
}

@Composable
private fun TeamInformationScreen(
    modifier: Modifier = Modifier,
    uiState: TeamInformationState,
    navigateUp: () -> Unit,
    onClickMyPageItem: (TeamItem) -> Unit,
    onDialogAction: () -> Unit
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
                    stringId = R.string.setting_team_information
                ),
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier.padding(innerPadding),
            contentPadding = PaddingValues(vertical = 8.dp),
        ) {
            items(TeamItem.entries, key = { it.name }) { item ->
                val interactionSource = remember { MutableInteractionSource() }

                MyPageCard(
                    title = stringResource(item.titleResId),
                    value = item.getValue(teamInformation = uiState.teamInformation),
                    textColor = item.textColor,
                    enabledTitle = item.enabledTitle,
                    enabledClick = item.enabledClick,
                    iconContent = item.iconResId?.let {
                        {
                            Box(
                                modifier = Modifier
                                    .clickable(
                                        interactionSource = interactionSource,
                                        indication = ripple(
                                            bounded = false,
                                            radius = 40.dp,
                                            color = SubText
                                        ),
                                        onClick = { onClickMyPageItem(item) }
                                    ),
                            ) {
                                Icon(
                                    painter = painterResource(id = it),
                                    contentDescription = null,
                                    tint = item.textColor
                                )
                            }
                        }
                    },
                    onClick = { onClickMyPageItem(item) }
                )
            }
        }
        uiState.dialogMessage?.let { error ->
            BasicDialog(
                title = error.title,
                content = error.message,
                positiveButton = error.positiveButton,
                negativeButton = BasicDialogButton(
                    text = stringResource(R.string.dialog_negative_button),
                    backgroundColor = SubBackground,
                    onClick = onDialogAction
                ),
            )
        }
    }

    LoadingIndicator(
        isLoading = uiState.isLoading,
    )

}

@Preview
@Composable
private fun TeamInformationScreenPreview() {
    TeamInformationScreen(
        uiState = TeamInformationState.init(),
        navigateUp = { },
        onClickMyPageItem = {  },
        onDialogAction = { }
    )
}