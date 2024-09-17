package com.easyhz.patchnote.ui.screen.sign.name

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
import com.easyhz.patchnote.core.designSystem.component.sign.SignField
import com.easyhz.patchnote.core.designSystem.component.topbar.TopBar
import com.easyhz.patchnote.core.designSystem.util.topbar.TopBarType
import com.easyhz.patchnote.ui.screen.sign.name.contract.NameIntent
import com.easyhz.patchnote.ui.screen.sign.name.contract.NameSideEffect
import com.easyhz.patchnote.ui.theme.MainText

@Composable
fun SignNameScreen(
    modifier: Modifier = Modifier,
    viewModel: SignNameViewModel = hiltViewModel(),
    uid: String,
    phoneNumber: String,
    navigateToUp: () -> Unit,
    navigateToHome: () -> Unit
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
                    onClick = { viewModel.postIntent(NameIntent.NavigateToUp) }
                )
            )
        }
    ) { innerPadding ->
        SignField(
            modifier = Modifier.padding(innerPadding).padding(vertical = 24.dp, horizontal = 20.dp),
            title = stringResource(R.string.sign_name_title),
            subTitle = stringResource(R.string.sign_name_subTitle),
            value = uiState.nameText,
            placeholder = stringResource(R.string.sign_name_placeholder),
            onValueChange = { viewModel.postIntent(NameIntent.ChangeNameText(it)) },
            enabledButton = uiState.enabledButton,
        ) {
            viewModel.postIntent(NameIntent.SaveUser(uid, phoneNumber))
        }
    }

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when(sideEffect) {
            is NameSideEffect.NavigateToUp -> navigateToUp()
            is NameSideEffect.NavigateToHome -> { navigateToHome() }
        }
    }
}
