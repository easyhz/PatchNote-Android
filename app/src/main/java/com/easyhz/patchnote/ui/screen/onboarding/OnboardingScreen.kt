package com.easyhz.patchnote.ui.screen.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.designSystem.button.MainButton
import com.easyhz.patchnote.core.designSystem.onboarding.OnboardingInformation
import com.easyhz.patchnote.core.designSystem.onboarding.OnboardingInformationType
import com.easyhz.patchnote.ui.theme.Bold34
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.SemiBold20

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    navigateToSign: () -> Unit
) {
    Scaffold(
        bottomBar = {
            MainButton(
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 20.dp).fillMaxWidth(),
                text = stringResource(id = R.string.onboarding_button_start),
                onClick = {

                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).padding(horizontal = 28.dp).padding(top = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(76.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.onboarding_title),
                    style = Bold34,
                    color = MainText
                )
                Text(
                    text = stringResource(id = R.string.onboarding_subtitle),
                    style = SemiBold20,
                    color = MainText
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(40.dp)
            ) {
                OnboardingInformationType.entries.forEach { type ->
                    OnboardingInformation(
                        informationType = type
                    )
                }
            }
        }
    }
}