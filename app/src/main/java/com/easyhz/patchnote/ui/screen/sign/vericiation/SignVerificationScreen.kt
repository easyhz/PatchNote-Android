package com.easyhz.patchnote.ui.screen.sign.vericiation

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
import com.easyhz.patchnote.ui.screen.sign.vericiation.contract.VerificationIntent
import com.easyhz.patchnote.ui.screen.sign.vericiation.contract.VerificationSideEffect
import com.easyhz.patchnote.ui.theme.LocalSnackBarHostState
import com.easyhz.patchnote.ui.theme.MainText

@Composable
fun SignVerificationScreen(
    modifier: Modifier = Modifier,
    viewModel: SignVerificationViewModel = hiltViewModel(),
    verificationId: String,
    phoneNumber: String,
    navigateToUp: () -> Unit,
    navigateToName: (uid: String, phoneNumber: String) -> Unit,
    navigateToTeam: (uid: String, phoneNumber: String, userName: String) -> Unit,
    navigateToTeamSelection: () -> Unit
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
                    onClick = { viewModel.postIntent(VerificationIntent.NavigateToUp) }
                )
            )
        }
    ) { innerPadding ->
        SignField(
            modifier = Modifier.padding(innerPadding).padding(vertical = 24.dp, horizontal = 20.dp),
            title = stringResource(R.string.sign_verification_title),
            subTitle = stringResource(R.string.sign_verification_subTitle),
            value = uiState.codeText,
            placeholder = stringResource(R.string.sign_verification_placeholder),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            onValueChange = { viewModel.postIntent(VerificationIntent.ChangeVerificationCodeText(it)) },
            enabledButton = uiState.enabledButton,
        ) {
            viewModel.postIntent(VerificationIntent.RequestVerification(verificationId, phoneNumber))
        }
    }

    LoadingIndicator(
        isLoading = uiState.isLoading,
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when(sideEffect) {
            is VerificationSideEffect.NavigateToUp -> { navigateToUp() }
            is VerificationSideEffect.NavigateToName -> { navigateToName(sideEffect.uid, sideEffect.phoneNumber) }
            is VerificationSideEffect.NavigateToTeam -> { navigateToTeam(sideEffect.uid, sideEffect.phoneNumber, sideEffect.userName) }
            is VerificationSideEffect.NavigateToTeamSelection -> { navigateToTeamSelection() }
            is VerificationSideEffect.ShowSnackBar -> {
                snackBarHost.showSnackbar(
                    message = sideEffect.value,
                    withDismissAction = true
                )
            }
        }

    }
}