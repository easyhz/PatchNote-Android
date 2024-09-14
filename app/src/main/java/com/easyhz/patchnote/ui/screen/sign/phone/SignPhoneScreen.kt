package com.easyhz.patchnote.ui.screen.sign.phone

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
import com.easyhz.patchnote.ui.screen.sign.phone.contract.PhoneIntent
import com.easyhz.patchnote.ui.screen.sign.phone.contract.PhoneSideEffect
import com.easyhz.patchnote.ui.theme.MainText

@Composable
fun SignPhoneScreen(
    modifier: Modifier = Modifier,
    viewModel: SignPhoneViewModel = hiltViewModel(),
    navigateToUp: () -> Unit
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
                    onClick = { viewModel.postIntent(PhoneIntent.NavigateToUp) }
                )
            )
        }
    ) { innerPadding ->
        SignField(
            modifier = Modifier.padding(innerPadding).padding(vertical = 24.dp, horizontal = 20.dp),
            title = stringResource(R.string.sign_phone_title),
            subTitle = stringResource(R.string.sign_phone_subTitle),
            value = uiState.phoneText,
            placeholder = stringResource(R.string.sign_phone_placeholder),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            onValueChange = { viewModel.postIntent(PhoneIntent.ChangePhoneText(it)) },
        ) {

        }
    }

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when(sideEffect) {
            is PhoneSideEffect.NavigateToUp -> navigateToUp()
        }

    }
}