package com.easyhz.patchnote.core.designSystem.component.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.R
import com.easyhz.patchnote.ui.theme.Caption
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Medium14
import com.easyhz.patchnote.ui.theme.Primary
import com.easyhz.patchnote.ui.theme.SemiBold16

@Composable
fun OnboardingInformation(
    modifier: Modifier = Modifier,
    informationType: OnboardingInformationType,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = informationType.iconId),
            contentDescription = informationType.iconId.toString(),
            tint = Primary
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = stringResource(informationType.titleId),
                style = SemiBold16,
                color = MainText
            )
            Text(
                text = stringResource(informationType.contentId),
                style = Medium14,
                color = Caption
            )
        }
    }
}

enum class OnboardingInformationType(
    @StringRes val titleId: Int,
    @StringRes val contentId: Int,
    @DrawableRes val iconId: Int
) {
    RECEIPT(
        titleId = R.string.onboarding_receipt_title,
        contentId = R.string.onboarding_receipt_content,
        iconId = R.drawable.ic_report
    ),
    SEARCH(
        titleId = R.string.onboarding_search_title,
        contentId = R.string.onboarding_search_content,
        iconId = R.drawable.ic_search
    ),
    SUCCESS(
        titleId = R.string.onboarding_success_title,
        contentId = R.string.onboarding_success_content,
        iconId = R.drawable.ic_check
    )
}