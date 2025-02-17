package com.easyhz.patchnote.core.model.onboarding

import com.easyhz.patchnote.R

enum class OfflineDefectOnboardingStep: OnboardingStep {
    STEP_1 {
        override val imageId: Int
            get() = R.drawable.offline_tootip_1
    }, STEP_2 {
        override val imageId: Int
            get() = R.drawable.offline_tootip_2
    }, STEP_3 {
        override val imageId: Int
            get() = R.drawable.offline_tootip_3
    }, STEP_4 {
        override val imageId: Int
            get() = R.drawable.offline_tootip_4
    },
}