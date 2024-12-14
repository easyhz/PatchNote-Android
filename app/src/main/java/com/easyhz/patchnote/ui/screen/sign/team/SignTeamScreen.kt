package com.easyhz.patchnote.ui.screen.sign.team

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.designSystem.component.scaffold.PatchNoteScaffold
import com.easyhz.patchnote.core.designSystem.component.sign.SignField
import com.easyhz.patchnote.core.designSystem.component.topbar.TopBar
import com.easyhz.patchnote.core.designSystem.util.topbar.TopBarType
import com.easyhz.patchnote.ui.theme.MainText

@Composable
fun SignTeamScreen(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
    navigateToCreateTeam: () -> Unit
) {
    PatchNoteScaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                left = TopBarType.TopBarIconButton(
                    iconId = R.drawable.ic_arrow_leading,
                    iconAlignment = Alignment.CenterStart,
                    tint = MainText,
                    onClick = { }
                )
            )
        }
    ) { innerPadding ->
        SignField(
            modifier = Modifier.padding(innerPadding).padding(vertical = 24.dp, horizontal = 20.dp),
            title = stringResource(R.string.sign_team_title),
            subTitle = stringResource(R.string.sign_team_subTitle),
            value = "",
            placeholder = stringResource(R.string.sign_team_placeholder),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            onValueChange = { },
            enabledButton = true,
            mainButtonText = stringResource(R.string.sign_team_button),
        ) {

        }
    }
}