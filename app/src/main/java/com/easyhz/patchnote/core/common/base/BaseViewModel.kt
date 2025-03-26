package com.easyhz.patchnote.core.common.base

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.util.resource.ResourceHelper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * MVI 를 위한 [BaseViewModel]
 *
 * [UiState] State : 상태
 * [UiIntent] Intent : 의도
 * [SideEffect] SideEffect: 부수 효과
 */
abstract class BaseViewModel<State: UiState, Intent: UiIntent, SideEffect: UiSideEffect>(
    initialState: State
): ViewModel() {
    protected val currentState: State
        get() = uiState.value

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState: StateFlow<State>
        get() = _uiState.asStateFlow()

    private val _intent: MutableSharedFlow<Intent> = MutableSharedFlow()
    val intent = _intent.asSharedFlow()

    private val _sideEffect: MutableSharedFlow<SideEffect> = MutableSharedFlow()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        subscribeIntent()
    }

    /**
     * [Intent] 구독
     */
    private fun subscribeIntent() = viewModelScope.launch {
        intent.collect { handleIntent(it) }
    }

    /**
     * [Intent] 핸들러
     */
    protected abstract fun handleIntent(intent: Intent)

    /**
     * [State] 설정
     */
    fun reduce(reducer: State.() -> State) { _uiState.value = currentState.reducer() }

    /**
     * [Intent] 설정
     */
    fun postIntent(intent: Intent) = viewModelScope.launch { _intent.emit(intent) }

    /**
     * [SideEffect] 설정
     */
    fun postSideEffect(builder: () -> SideEffect) = viewModelScope.launch { _sideEffect.emit(builder()) }

    fun showSnackBar(
        resourceHelper: ResourceHelper,
        @StringRes value: Int,
        vararg formatArgs: Any,
        sideEffect: (String) -> SideEffect
    ) {
        val snackBarString = resourceHelper.getString(value, *formatArgs)
        postSideEffect { sideEffect(snackBarString) }
    }
}