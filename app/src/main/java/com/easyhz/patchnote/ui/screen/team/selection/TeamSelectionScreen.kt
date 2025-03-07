package com.easyhz.patchnote.ui.screen.team.selection

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.designSystem.component.card.TeamCard
import com.easyhz.patchnote.core.designSystem.component.scaffold.PatchNoteScaffold
import com.easyhz.patchnote.core.designSystem.component.topbar.TopBar
import com.easyhz.patchnote.core.designSystem.util.topbar.TopBarType
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Primary
import com.easyhz.patchnote.ui.theme.Regular16
import com.easyhz.patchnote.ui.theme.SubText

@Composable
private fun TeamSelectionScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    onClickAdd: () -> Unit,
    onClickTeam: () -> Unit,
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
            items(5) {
                TeamCard(
                    teamName = "팀 $it",
                    onClick = { onClickTeam() }
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
}

@Preview
@Composable
private fun TeamSelectionScreenPreview() {
    TeamSelectionScreen(
        navigateUp = { },
        onClickAdd = { },
        onClickTeam = { }
    )
}