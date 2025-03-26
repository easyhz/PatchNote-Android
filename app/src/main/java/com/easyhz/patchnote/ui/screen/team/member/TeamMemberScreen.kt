package com.easyhz.patchnote.ui.screen.team.member

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easyhz.patchnote.core.common.util.collectInSideEffectWithLifecycle
import com.easyhz.patchnote.core.designSystem.component.scaffold.PatchNoteScaffold
import com.easyhz.patchnote.ui.screen.team.member.contract.TeamMemberState

/**
 * Date: 2025. 3. 26.
 * Time: 오후 4:26
 */

@Composable
fun TeamMemberScreen(
    modifier: Modifier = Modifier,
    viewModel: TeamMemberViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TeamMemberScreen(
        modifier = modifier,
        uiState = uiState
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        TODO("Not yet implemented")
        when (sideEffect) {

        }
    }
}

@Composable
private fun TeamMemberScreen(
    modifier: Modifier = Modifier,
    uiState: TeamMemberState,
) {
    PatchNoteScaffold(
        topBar = {

        }
    ) {

    }
}

@Preview
@Composable
private fun TeamMemberScreenPreview() {
    TeamMemberScreen(
        uiState = TeamMemberState.init()
    )
}