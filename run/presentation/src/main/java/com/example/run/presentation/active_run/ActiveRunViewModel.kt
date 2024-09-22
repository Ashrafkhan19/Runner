package com.example.run.presentation.active_run

import ActiveRunState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.run.domain.RunningTracker
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

class ActiveRunViewModel(
    private val runningTracker: RunningTracker
) : ViewModel() {

    var state by mutableStateOf(ActiveRunState())
        private set

    private val eventChannel = Channel<ActiveRunEvent>()
    val events = eventChannel.receiveAsFlow()

    private val hasLocationPermission = MutableStateFlow(false)

    private val shouldTrack = snapshotFlow { state.shouldTrack }.stateIn(
        viewModelScope, SharingStarted.Lazily, state.shouldTrack
    )

    private val isTracking =
        shouldTrack.combine(hasLocationPermission) { shouldTrack, hasLocationPermission ->
            shouldTrack && hasLocationPermission
        }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    init {

        hasLocationPermission.onEach {
            if (it) {
                runningTracker.startObservingLocation()
            } else runningTracker.stopObservingLocation()
        }.launchIn(viewModelScope)

        isTracking.onEach {
            runningTracker.setIsTracking(it)
        }.launchIn(viewModelScope)

        runningTracker.currentLocation.onEach {
            state = state.copy(
                currentLocation = it?.location
            )
        }.launchIn(viewModelScope)

        runningTracker
            .runData
            .onEach {
                state = state.copy(runData = it)
            }
            .launchIn(viewModelScope)

        runningTracker
            .elapsedTime
            .onEach {
                state = state.copy(
                    elapsedTime = it
                )
            }
            .launchIn(viewModelScope)


    }

    fun onAction(action: ActiveRunAction) {
        when (action) {
            ActiveRunAction.OnBackClick -> {
                state = state.copy(shouldTrack = false)
            }

            ActiveRunAction.OnFinishRunClick -> {}
            ActiveRunAction.OnResumeRunClick -> {
                state = state.copy(
                    shouldTrack = true
                )
            }

            ActiveRunAction.OnToggleRunClick -> {
                state = state.copy(
                    hasStartedRunning = true,
                    shouldTrack = !state.shouldTrack
                )
            }

            is ActiveRunAction.SubmitLocationPermissionInfo -> {
                hasLocationPermission.value = action.acceptedLocationPermission
                state = state.copy(
                    showLocationRationale = action.shouldShowLocationRational
                )

            }

            is ActiveRunAction.SubmitNotificationPermissionInfo -> {
                state = state.copy(
                    showNotificationRationale = action.shouldShowNotificationRational
                )
            }

            ActiveRunAction.DismissRationaleDialog -> {
                state = state.copy(
                    showNotificationRationale = false, showLocationRationale = false
                )
            }
        }
    }
}