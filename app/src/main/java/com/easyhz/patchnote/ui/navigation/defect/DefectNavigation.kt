package com.easyhz.patchnote.ui.navigation.defect

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.easyhz.patchnote.ui.screen.defectEntry.DefectEntryScreen

internal fun NavGraphBuilder.defectGraph(
    navController: NavController
) {
    composable<DefectEntry> {
        DefectEntryScreen {  }
    }
}