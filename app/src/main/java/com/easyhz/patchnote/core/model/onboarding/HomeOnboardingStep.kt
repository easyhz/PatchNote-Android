package com.easyhz.patchnote.core.model.onboarding

import com.easyhz.patchnote.R


enum class HomeOnboardingStep: OnboardingStep {
    STEP_1 {
        override val imageId: Int
            get() = R.drawable.tootip_1
    }, STEP_2 {
        override val imageId: Int
            get() = R.drawable.tootip_2
    }, STEP_3 {
        override val imageId: Int
            get() = R.drawable.tootip_3
    }, STEP_4 {
        override val imageId: Int
            get() = R.drawable.tootip_4
    }, STEP_5 {
        override val imageId: Int
            get() = R.drawable.tootip_5
    },
}