package com.easyhz.patchnote.ui.screen.setting.team

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.error.AppError
import com.easyhz.patchnote.core.common.error.handleError
import com.easyhz.patchnote.core.common.util.log.Logger
import com.easyhz.patchnote.core.common.util.resource.ResourceHelper
import com.easyhz.patchnote.core.designSystem.util.dialog.BasicDialogButton
import com.easyhz.patchnote.core.model.error.DialogMessage
import com.easyhz.patchnote.core.model.setting.TeamItem
import com.easyhz.patchnote.domain.usecase.team.FetchTeamUseCase
import com.easyhz.patchnote.domain.usecase.team.LeaveTeamUseCase
import com.easyhz.patchnote.ui.screen.setting.team.contract.TeamInformationIntent
import com.easyhz.patchnote.ui.screen.setting.team.contract.TeamInformationSideEffect
import com.easyhz.patchnote.ui.screen.setting.team.contract.TeamInformationState
import com.easyhz.patchnote.ui.theme.MainBackground
import com.easyhz.patchnote.ui.theme.Red
import com.easyhz.patchnote.ui.theme.SemiBold18
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Date: 2025. 3. 26.
 * Time: 오후 2:20
 */

@HiltViewModel
class TeamInformationViewModel @Inject constructor(
    private val logger: Logger,
    private val resourceHelper: ResourceHelper,
    private val fetchTeamUseCase: FetchTeamUseCase,
    private val leaveTeamUseCase: LeaveTeamUseCase,
) : BaseViewModel<TeamInformationState, TeamInformationIntent, TeamInformationSideEffect>(
    initialState = TeamInformationState.init()
) {
    private val tag = "TeamInformationViewModel"
    override fun handleIntent(intent: TeamInformationIntent) {
        when (intent) {
            is TeamInformationIntent.ClickTeamInformationItem -> onClickTeamInformationItem(intent.teamItem)
            is TeamInformationIntent.NavigateUp -> navigateUp()
            is TeamInformationIntent.ShowError -> handleDialog(intent.message)
        }
    }

    init {
        fetchTeamInformation()
    }

    private fun fetchTeamInformation() {
        viewModelScope.launch {
            fetchTeamUseCase.invoke(Unit).onSuccess {
                reduce { copy(teamInformation = it) }
            }.onFailure { e ->
                handleError(message = "fetchTeamInformation", throwable = e)
            }.also {
                setLoading(false)
            }
        }
    }


    private fun onClickTeamInformationItem(item: TeamItem) {
        when(item) {
            TeamItem.INVITE_CODE -> onClickTeamInviteCode()
            TeamItem.DATA_MANAGEMENT -> navigateToDataManagement()
            TeamItem.MEMBER -> navigateToMember()
            TeamItem.LEAVE -> setDialogDetail(item)
            else -> { }
        }
    }

    private fun navigateUp() {
        postSideEffect { TeamInformationSideEffect.NavigateUp }
    }

    private fun onClickTeamInviteCode() {
        postSideEffect { TeamInformationSideEffect.CopyTeamInviteCode(currentState.teamInformation.team.inviteCode) }
    }

    private fun navigateToDataManagement() {
        postSideEffect { TeamInformationSideEffect.NavigateToDataManagement }
    }

    private fun navigateToMember() {
        postSideEffect { TeamInformationSideEffect.NavigateToMember }
    }

    private fun navigateToSplash() {
        postSideEffect { TeamInformationSideEffect.NavigateToSplash }
    }

    private fun setDialogDetail(item: TeamItem) {
        val (title, message, positiveButton) = getDialogTitleAndMessageAndPositiveButton(item)

        val dialogMessage = DialogMessage(
            title = title,
            message = message,
            positiveButton = positiveButton,
        )

        handleDialog(dialogMessage)
    }

    private fun handleDialog(message: DialogMessage?) {
        reduce { copy(dialogMessage = message) }
    }


    private fun leaveTeam() {
        handleDialog(null)
        viewModelScope.launch {
            setLoading(true)
            val teamId = currentState.teamInformation.team.id
            leaveTeamUseCase.invoke(teamId).onSuccess {
                navigateToSplash()
            }.onFailure { e ->
                handleError(message = "leaveTeam", throwable = e)
                showSnackBar(resourceHelper, e.handleError()) {
                    TeamInformationSideEffect.ShowSnackBar(it)
                }
            }.also {
                setLoading(false)
            }
        }
    }

    private fun getDialogTitleAndMessageAndPositiveButton(item: TeamItem): Triple<String, String, BasicDialogButton?> {
        val resId = when (item) {

            TeamItem.LEAVE -> Triple(
                resourceHelper.getString(R.string.leave_team_dialog_title),
                resourceHelper.getString(R.string.leave_team_dialog_message, currentState.teamInformation.team.name),
                getDefaultPositiveButton(
                    text = resourceHelper.getString(R.string.leave_team_dialog_positive_button),
                    onClick = ::leaveTeam
                )
            )

            else -> Triple("", "", null)
        }
        return resId
    }


    private fun getDefaultPositiveButton(text: String, onClick: () -> Unit): BasicDialogButton {
        return BasicDialogButton(
            text = text,
            style = SemiBold18.copy(color = MainBackground),
            backgroundColor = Red,
            onClick = onClick
        )
    }

    private fun setLoading(isLoading: Boolean) {
        if (currentState.isLoading == isLoading) return
        reduce { copy(isLoading = isLoading) }
    }

    private fun handleError(message: String, throwable: Throwable) {
        logger.e(tag, "$message : ${throwable.message}", throwable)
        when (throwable) {
            AppError.NoTeamDataError -> {
                navigateToSplash()
            }
            else -> {
                Log.e(tag, "handleError : ${throwable.message}", throwable)
            }
        }
    }
}