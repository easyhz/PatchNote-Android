package com.easyhz.patchnote.ui.screen.setting.my_page

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.easyhz.patchnote.core.model.setting.MyPageItem
import com.easyhz.patchnote.ui.screen.setting.my_page.contract.MyPageIntent
import com.easyhz.patchnote.ui.screen.setting.my_page.contract.MyPageSideEffect
import com.easyhz.patchnote.ui.theme.LocalSnackBarHostState
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.SubBackground
import com.easyhz.patchnote.ui.theme.SubText

@Composable
fun MyPageScreen(
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel = hiltViewModel(),
    navigateToUp: () -> Unit,
    navigateToOnboarding: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackBarHostState = LocalSnackBarHostState.current
    val interactionSource = remember { MutableInteractionSource() }

    PatchNoteScaffold(
        topBar = {
            TopBar(
                left = TopBarType.TopBarIconButton(
                    iconId = R.drawable.ic_arrow_leading,
                    iconAlignment = Alignment.CenterStart,
                    tint = MainText,
                    onClick = { viewModel.postIntent(MyPageIntent.NavigateToUp) }
                ),
                title = TopBarType.TopBarTitle(
                    stringId = R.string.setting_my_page
                ),
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier.padding(innerPadding),
            contentPadding = PaddingValues(vertical = 8.dp),
        ) {
            items(MyPageItem.entries, key = { it.name }) { item ->
                MyPageCard(
                    title = stringResource(item.titleResId),
                    value = item.getValue(userInformation = uiState.userInformation),
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
                                    ) {
                                        viewModel.postIntent(
                                            MyPageIntent.ClickMyPageItem(item)
                                        )
                                    },
                            ) {
                                Icon(
                                    painter = painterResource(id = it),
                                    contentDescription = null,
                                    tint = item.textColor
                                )
                            }
                        }
                    },
                ) {
                    viewModel.postIntent(MyPageIntent.ClickMyPageItem(item))
                }
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
                    onClick = { viewModel.postIntent(MyPageIntent.ShowError(null)) }
                ),
            )
        }
    }

    LoadingIndicator(
        isLoading = uiState.isLoading,
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is MyPageSideEffect.NavigateToUp -> {
                navigateToUp()
            }

            is MyPageSideEffect.NavigateToOnboarding -> {
                navigateToOnboarding()
            }

            is MyPageSideEffect.ShowSnackBar -> {
                snackBarHostState.showSnackbar(
                    message = sideEffect.value,
                    withDismissAction = true
                )
            }
        }
    }

}