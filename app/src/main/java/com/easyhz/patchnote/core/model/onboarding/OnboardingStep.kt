package com.easyhz.patchnote.core.model.onboarding

import androidx.annotation.DrawableRes
import com.easyhz.patchnote.R

enum class OnboardingStep(
    @DrawableRes val imageId: Int,
){
    STEP_1(
        imageId = R.drawable.tootip_1
    ), STEP_2(
        imageId = R.drawable.tootip_2
    ), STEP_3(
        imageId = R.drawable.tootip_3
    ), STEP_4(
        imageId = R.drawable.tootip_4
    ), STEP_5(
        imageId = R.drawable.tootip_5
    )
}