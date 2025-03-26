package com.easyhz.patchnote.ui.screen.team.member

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.util.collectInSideEffectWithLifecycle
import com.easyhz.patchnote.core.designSystem.component.card.MemberCard
import com.easyhz.patchnote.core.designSystem.component.loading.LoadingIndicator
import com.easyhz.patchnote.core.designSystem.component.scaffold.PatchNoteScaffold
import com.easyhz.patchnote.core.designSystem.component.topbar.TopBar
import com.easyhz.patchnote.core.designSystem.util.topbar.TopBarType
import com.easyhz.patchnote.core.model.user.User
import com.easyhz.patchnote.ui.screen.team.member.contract.TeamMemberIntent
import com.easyhz.patchnote.ui.screen.team.member.contract.TeamMemberSideEffect
import com.easyhz.patchnote.ui.screen.team.member.contract.TeamMemberState
import com.easyhz.patchnote.ui.theme.LocalSnackBarHostState
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Medium18
import com.easyhz.patchnote.ui.theme.SubText

/**
 * Date: 2025. 3. 26.
 * Time: 오후 4:26
 */

@Composable
fun TeamMemberScreen(
    modifier: Modifier = Modifier,
    viewModel: TeamMemberViewModel = hiltViewModel(),
    navigateUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackBarHostState = LocalSnackBarHostState.current

    TeamMemberScreen(
        modifier = modifier,
        uiState = uiState,
        navigateUp = { viewModel.postIntent(TeamMemberIntent.NavigateUp) }
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is TeamMemberSideEffect.NavigateUp -> navigateUp()
            is TeamMemberSideEffect.ShowSnackBar -> {
                snackBarHostState.showSnackbar(
                    message = sideEffect.value,
                    withDismissAction = true
                )
            }
        }
    }
}

@Composable
private fun TeamMemberScreen(
    modifier: Modifier = Modifier,
    uiState: TeamMemberState,
    navigateUp: () -> Unit
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
                    stringId = R.string.setting_team_member
                ),
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier.padding(innerPadding)
        ) {
            itemsIndexed(uiState.members, key = { _, it -> it.id }) { index, member ->
                MemberCard(
                    name = member.name,
                    trailing = if (index == 0) {
                        {
                            Text(
                                text = stringResource(R.string.setting_team_admin),
                                style = Medium18.copy(
                                    color = SubText
                                )
                            )
                        }
                    } else null
                )
            }
        }
    }

    LoadingIndicator(
        isLoading = uiState.isLoading,
    )
}

@Preview
@Composable
private fun TeamMemberScreenPreview() {
    TeamMemberScreen(
        uiState = TeamMemberState.init().copy(
            members = listOf(
                User.Empty.copy(
                    id = "1",
                    name = "Name1"
                ), User.Empty.copy(
                    id = "2",
                    name = "Name1"
                ),
            )
        ),
        navigateUp = { }
    )
}