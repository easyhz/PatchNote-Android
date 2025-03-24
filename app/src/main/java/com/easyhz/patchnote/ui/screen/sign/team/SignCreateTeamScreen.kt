package com.easyhz.patchnote.ui.screen.sign.team

import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.util.collectInSideEffectWithLifecycle
import com.easyhz.patchnote.core.designSystem.component.loading.LoadingIndicator
import com.easyhz.patchnote.core.designSystem.component.scaffold.PatchNoteScaffold
import com.easyhz.patchnote.core.designSystem.component.sign.SignField
import com.easyhz.patchnote.core.designSystem.component.topbar.TopBar
import com.easyhz.patchnote.core.designSystem.util.topbar.TopBarType
import com.easyhz.patchnote.ui.screen.sign.team.contract.SignCreateTeamIntent
import com.easyhz.patchnote.ui.screen.sign.team.contract.SignCreateTeamSideEffect
import com.easyhz.patchnote.ui.theme.LocalSnackBarHostState
import com.easyhz.patchnote.ui.theme.MainText


@Composable
fun SignCreateTeamScreen(
    modifier: Modifier = Modifier,
    viewModel: SignCreateTeamViewModel = hiltViewModel(),
    navigateToTeamSelection: () -> Unit,
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
                    onClick = { viewModel.postIntent(SignCreateTeamIntent.NavigateToUp) }
                )
            )
        }
    ) { innerPadding ->
        SignField(
            modifier = Modifier
                .imePadding()
                .padding(innerPadding)
                .padding(vertical = 24.dp, horizontal = 20.dp),
            title = stringResource(R.string.sign_create_team_title),
            subTitle = stringResource(R.string.sign_create_team_subTitle),
            value = uiState.teamNameText,
            placeholder = stringResource(R.string.sign_create_team_placeholder),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = { viewModel.postIntent(SignCreateTeamIntent.ChangeTeamNameText(it)) },
            enabledButton = uiState.enabledButton,
            mainButtonText = stringResource(R.string.sign_create_team_button),
        ) { viewModel.postIntent(SignCreateTeamIntent.ClickMainButton) }
    }

    LoadingIndicator(
        isLoading = uiState.isLoading,
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is SignCreateTeamSideEffect.ShowSnackBar -> {
                snackBarHost.showSnackbar(
                    message = sideEffect.message,
                    withDismissAction = true
                )
            }
            is SignCreateTeamSideEffect.NavigateToTeamSelection -> navigateToTeamSelection()
            is SignCreateTeamSideEffect.NavigateToUp -> navigateToUp()
        }
    }
}