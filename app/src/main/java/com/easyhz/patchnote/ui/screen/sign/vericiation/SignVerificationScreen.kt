package com.easyhz.patchnote.ui.screen.sign.vericiation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
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
import com.easyhz.patchnote.core.designSystem.component.sign.SignField
import com.easyhz.patchnote.core.designSystem.component.topbar.TopBar
import com.easyhz.patchnote.core.designSystem.util.topbar.TopBarType
import com.easyhz.patchnote.ui.screen.sign.vericiation.contract.VerificationIntent
import com.easyhz.patchnote.ui.screen.sign.vericiation.contract.VerificationSideEffect
import com.easyhz.patchnote.ui.theme.MainText

@Composable
fun SignVerificationScreen(
    modifier: Modifier = Modifier,
    viewModel: SignVerificationViewModel = hiltViewModel(),
    verificationId: String,
    navigateToUp: () -> Unit,
    navigateToName: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                left = TopBarType.TopBarIconButton(
                    iconId = R.drawable.ic_arrow_left_leading,
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
            viewModel.postIntent(VerificationIntent.RequestVerification(verificationId))
        }
    }

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when(sideEffect) {
            is VerificationSideEffect.NavigateToUp -> { navigateToUp() }
            is VerificationSideEffect.NavigateToName -> { navigateToName() }
        }

    }
}