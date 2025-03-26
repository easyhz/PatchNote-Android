package com.easyhz.patchnote.ui.screen.team.selection

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.util.collectInSideEffectWithLifecycle
import com.easyhz.patchnote.core.designSystem.component.card.TeamCard
import com.easyhz.patchnote.core.designSystem.component.loading.LoadingIndicator
import com.easyhz.patchnote.core.designSystem.component.scaffold.PatchNoteScaffold
import com.easyhz.patchnote.core.designSystem.component.topbar.TopBar
import com.easyhz.patchnote.core.designSystem.util.topbar.TopBarType
import com.easyhz.patchnote.core.model.team.Team
import com.easyhz.patchnote.ui.screen.team.selection.contract.TeamSelectionIntent
import com.easyhz.patchnote.ui.screen.team.selection.contract.TeamSelectionSideEffect
import com.easyhz.patchnote.ui.screen.team.selection.contract.TeamSelectionState
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Primary
import com.easyhz.patchnote.ui.theme.Regular16
import com.easyhz.patchnote.ui.theme.SubText


@Composable
fun TeamSelectionScreen(
    modifier: Modifier = Modifier,
    viewModel: TeamSelectionViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToHome: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TeamSelectionScreen(
        modifier = modifier,
        uiState = uiState,
        navigateUp = { viewModel.postIntent(TeamSelectionIntent.NavigateUp) },
        onClickAdd = { viewModel.postIntent(TeamSelectionIntent.NavigateToTeamSign) },
        onClickTeam = { viewModel.postIntent(TeamSelectionIntent.SelectTeam(it)) }
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is TeamSelectionSideEffect.NavigateUp -> navigateUp()
            is TeamSelectionSideEffect.NavigateToTeamSign -> {
                // TODO: navigateToTeamSign()
            }
            is TeamSelectionSideEffect.NavigateToHome -> navigateToHome()
        }

    }
}

@Composable
private fun TeamSelectionScreen(
    modifier: Modifier = Modifier,
    uiState: TeamSelectionState,
    navigateUp: () -> Unit,
    onClickAdd: () -> Unit,
    onClickTeam: (Team) -> Unit,
) {
    PatchNoteScaffold(
        topBar = {
            TopBar(
                left = TopBarType.TopBarIconButton(
                    iconId = R.drawable.ic_arrow_leading,
                    iconAlignment = Alignment.CenterStart,
                    tint = MainText,
                    onClick = { navigateUp() }
                ),
                title = TopBarType.TopBarTitle(
                    stringId = R.string.team_selection_title
                ),
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier.padding(innerPadding)
        ) {
            item {
                TeamCard(
                    teamName = stringResource(R.string.team_selection_add),
                    painter = painterResource(R.drawable.ic_add),
                    color = Primary,
                    onClick = { onClickAdd() }
                )
            }
            items(uiState.teams, key = { it.id }) {
                TeamCard(
                    teamName = it.name,
                    onClick = { onClickTeam(it) }
                )
            }
            item {
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                    text = stringResource(R.string.team_selection_information),
                    style = Regular16.copy(
                        color = SubText,
                        lineHeight = 24.sp
                    )
                )
            }
        }
    }
    LoadingIndicator(
        isLoading = uiState.isLoading
    )
}

@Preview
@Composable
private fun TeamSelectionScreenPreview() {
    TeamSelectionScreen(
        uiState = TeamSelectionState.init(),
        navigateUp = { },
        onClickAdd = { },
        onClickTeam = { }
    )
}