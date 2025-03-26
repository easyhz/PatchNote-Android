package com.easyhz.patchnote.ui.screen.sign.name

import androidx.compose.foundation.layout.padding
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
import com.easyhz.patchnote.core.designSystem.component.loading.LoadingIndicator
import com.easyhz.patchnote.core.designSystem.component.scaffold.PatchNoteScaffold
import com.easyhz.patchnote.core.designSystem.component.sign.SignField
import com.easyhz.patchnote.core.designSystem.component.topbar.TopBar
import com.easyhz.patchnote.core.designSystem.util.topbar.TopBarType
import com.easyhz.patchnote.ui.screen.sign.name.contract.NameIntent
import com.easyhz.patchnote.ui.screen.sign.name.contract.NameSideEffect
import com.easyhz.patchnote.ui.theme.LocalSnackBarHostState
import com.easyhz.patchnote.ui.theme.MainText

@Composable
fun SignNameScreen(
    modifier: Modifier = Modifier,
    viewModel: SignNameViewModel = hiltViewModel(),
    navigateToUp: () -> Unit,
    navigateToTeam: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackBarHost = LocalSnackBarHostState.current
    PatchNoteScaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                left = TopBarType.TopBarIconButton(
                    iconId = R.drawable.ic_arrow_leading,
                    iconAlignment = Alignment.CenterStart,
                    tint = MainText,
                    onClick = { viewModel.postIntent(NameIntent.NavigateToUp) }
                )
            )
        }
    ) { innerPadding ->
        SignField(
            modifier = Modifier
                .padding(innerPadding)
                .padding(vertical = 24.dp, horizontal = 20.dp),
            title = stringResource(R.string.sign_name_title),
            subTitle = stringResource(R.string.sign_name_subTitle),
            value = uiState.nameText,
            placeholder = stringResource(R.string.sign_name_placeholder),
            onValueChange = { viewModel.postIntent(NameIntent.ChangeNameText(it)) },
            enabledButton = uiState.enabledButton,
        ) {
            viewModel.postIntent(NameIntent.ClickNextButton)
        }

    }

    LoadingIndicator(
        isLoading = uiState.isLoading,
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when(sideEffect) {
            is NameSideEffect.NavigateToUp -> navigateToUp()
            is NameSideEffect.NavigateToTeam -> { navigateToTeam() }
            is NameSideEffect.ShowSnackBar -> {
                snackBarHost.showSnackbar(
                    message = sideEffect.message,
                    withDismissAction = true
                )
            }
        }
    }
}
