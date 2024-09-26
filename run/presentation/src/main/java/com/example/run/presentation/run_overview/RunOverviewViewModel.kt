package com.example.run.presentation.run_overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.run.RunRepository
import com.example.core.domain.run.SyncRunScheduler
import com.example.core.domain.session.SessionStorage
import com.example.run.presentation.run_overview.mappper.toRunUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes

class RunOverviewViewModel(
    private val runRepository: RunRepository,
    private val runScheduler: SyncRunScheduler,
    private val applicationScope: CoroutineScope,
    private val session: SessionStorage
) : ViewModel() {

    var state by mutableStateOf(RunOverviewState())
        private set

    init {

        viewModelScope.launch {
            runScheduler.scheduleSync(SyncRunScheduler.SyncType.FetchRuns(15.minutes))
        }

        runRepository.getRuns().onEach {
            state = state.copy(runs = it.map { it.toRunUi() })
        }.launchIn(viewModelScope)

        viewModelScope.launch {
            runRepository.syncPendingRuns()
            runRepository.fetchRuns()
        }

    }

    fun onAction(action: RunOverviewAction) {
        when (action) {
            RunOverviewAction.OnAnalyticsClick -> {}
            RunOverviewAction.OnLogoutClick -> logOut()
            RunOverviewAction.OnStartClick -> {}
            is RunOverviewAction.DeleteRun -> {
                viewModelScope.launch {

                    runRepository.deleteRun(action.runUi.id)
                }
            }
        }
    }

    fun logOut() {
        applicationScope.launch {
            runScheduler.cancelAllSyncs()
            runRepository.deleteAllRuns()
            runRepository.logout()
            session.set(null)
        }
    }
}