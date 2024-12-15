package com.easyhz.patchnote.ui.screen.sign.team

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.util.collectInSideEffectWithLifecycle
import com.easyhz.patchnote.core.designSystem.component.dialog.BasicDialog
import com.easyhz.patchnote.core.designSystem.component.loading.LoadingIndicator
import com.easyhz.patchnote.core.designSystem.component.scaffold.PatchNoteScaffold
import com.easyhz.patchnote.core.designSystem.component.sign.SignField
import com.easyhz.patchnote.core.designSystem.component.topbar.TopBar
import com.easyhz.patchnote.core.designSystem.util.dialog.BasicDialogButton
import com.easyhz.patchnote.core.designSystem.util.topbar.TopBarType
import com.easyhz.patchnote.ui.screen.sign.team.contract.SignTeamIntent
import com.easyhz.patchnote.ui.screen.sign.team.contract.SignTeamSideEffect
import com.easyhz.patchnote.ui.theme.LocalSnackBarHostState
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Primary
import com.easyhz.patchnote.ui.theme.SemiBold16
import com.easyhz.patchnote.ui.theme.SemiBold18
import com.easyhz.patchnote.ui.theme.SubBackground

@Composable
fun SignTeamScreen(
    modifier: Modifier = Modifier,
    viewModel: SignTeamViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    navigateToCreateTeam: (uid: String, phoneNumber: String, userName: String) -> Unit,
    navigateToUp: () -> Unit,
) {
    val snackBarHost = LocalSnackBarHostState.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PatchNoteScaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                left = TopBarType.TopBarIconButton(
                    iconId = R.drawable.ic_arrow_leading,
                    iconAlignment = Alignment.CenterStart,
                    tint = MainText,
                    onClick = { viewModel.postIntent(SignTeamIntent.NavigateToUp) }
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .imePadding()
                .padding(innerPadding)
                .padding(vertical = 24.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SignField(
                title = stringResource(R.string.sign_team_title),
                subTitle = stringResource(R.string.sign_team_subTitle),
                value = uiState.teamCodeText,
                placeholder = stringResource(R.string.sign_team_placeholder),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                onValueChange = { viewModel.postIntent(SignTeamIntent.ChangeTeamCodeText(it)) },
                enabledButton = uiState.enabledButton,
                mainButtonText = stringResource(R.string.sign_team_button),
            ) { viewModel.postIntent(SignTeamIntent.RequestTeamCheck) }

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { viewModel.postIntent(SignTeamIntent.NavigateToCreateTeam) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.sign_team_create_team),
                    style = SemiBold16.copy(
                        textDecoration = TextDecoration.Underline
                    )
                )
            }
        }

        LoadingIndicator(
            isLoading = uiState.isLoading,
        )

        if (uiState.isShowTeamDialog) {
            BasicDialog(
                title = stringResource(R.string.sign_team_dialog_title, uiState.teamName),
                content = stringResource(R.string.sign_team_dialog_subTitle),
                positiveButton = BasicDialogButton(
                    text = stringResource(R.string.sign_team_dialog_positive_button),
                    style = SemiBold18.copy(color = Color.White),
                    backgroundColor = Primary,
                    onClick = { viewModel.postIntent(SignTeamIntent.ClickPositiveButton) }
                ),
                negativeButton = BasicDialogButton(
                    text = stringResource(R.string.sign_team_dialog_negative_button),
                    backgroundColor = SubBackground,
                    onClick = { viewModel.postIntent(SignTeamIntent.HideTeamDialog) }
                )
            )
        }
    }

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is SignTeamSideEffect.ShowSnackBar -> {
                snackBarHost.showSnackbar(
                    message = sideEffect.message,
                    withDismissAction = true
                )
            }
            is SignTeamSideEffect.NavigateToHome -> navigateToHome()
            is SignTeamSideEffect.NavigateToCreateTeam -> navigateToCreateTeam(uiState.uid, uiState.phoneNumber, uiState.userName)
            is SignTeamSideEffect.NavigateToUp -> navigateToUp()
        }
    }
}