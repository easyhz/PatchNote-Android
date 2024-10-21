package com.easyhz.patchnote.core.designSystem.util.bottomSheet

import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.designSystem.component.bottomSheet.BottomSheetType

enum class DefectDetailBottomSheet: BottomSheetType {
    DELETE {
        override val titleId: Int
            get() = R.string.delete
        override val iconId: Int
            get() = R.drawable.ic_delete
    }

}