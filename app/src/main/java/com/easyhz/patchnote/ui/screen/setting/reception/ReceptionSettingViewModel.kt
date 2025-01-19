package com.easyhz.patchnote.ui.screen.setting.reception

import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.domain.usecase.reception.GetReceptionSettingUseCase
import com.easyhz.patchnote.domain.usecase.reception.SetReceptionSettingUseCase
import com.easyhz.patchnote.ui.screen.setting.reception.contract.ReceptionSettingIntent
import com.easyhz.patchnote.ui.screen.setting.reception.contract.ReceptionSettingSideEffect
import com.easyhz.patchnote.ui.screen.setting.reception.contract.ReceptionSettingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceptionSettingViewModel @Inject constructor(
    private val getReceptionSettingUseCase: GetReceptionSettingUseCase,
    private val setReceptionSettingUseCase: SetReceptionSettingUseCase,
): BaseViewModel<ReceptionSettingState, ReceptionSettingIntent, ReceptionSettingSideEffect>(
    initialState = ReceptionSettingState.init()
) {

    override fun handleIntent(intent: ReceptionSettingIntent) {
        when(intent) {
            is ReceptionSettingIntent.NavigateToUp -> { navigateUp() }
            is ReceptionSettingIntent.ClickToggleButton -> { onClickToggleButton(intent.categoryType, intent.newValue) }
        }
    }

    init {
        init()
    }

    private fun init() {
        viewModelScope.launch {
            getReceptionSettingUseCase.invoke().collect {
                reduce { copy(items = it) }
            }
        }
    }


    private fun navigateUp() {
        postSideEffect { ReceptionSettingSideEffect.NavigateToUp }
    }

    private fun onClickToggleButton(categoryType: CategoryType, isChecked: Boolean) {
        viewModelScope.launch {
            setReceptionSettingUseCase.invoke(categoryType, isChecked).onFailure {

            }
        }
    }

}