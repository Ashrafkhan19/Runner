package com.example.core.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/**
 * A utility object that emits the elapsed time at regular intervals.
 *
 * This object creates a [Flow] that emits the duration between each emission.
 * It uses `System.currentTimeMillis()` to track elapsed time, ensuring the
 * duration between emissions is accurately calculated.
 */
object Timer {

    /**
     * Starts emitting the time elapsed between emissions in the form of a [Flow] of [Duration].
     *
     * The flow emits at a regular interval specified by the [interval] parameter.
     * Each emission represents the time passed since the previous emission, calculated
     * using `System.currentTimeMillis()`.
     *
     * @param interval The delay between each emission, specified as a [Duration].
     *                 Defaults to 200 milliseconds.
     * @return A [Flow] that emits the elapsed time between each emission as [Duration].
     *
     * Example usage:
     * ```
     * Timer.timeAndEmit(500.milliseconds).collect { elapsedTime ->
     *     println("Elapsed time: $elapsedTime")
     * }
     * ```
     */
    fun timeAndEmit(interval: Duration = 200.milliseconds): Flow<Duration> = flow {
        var lastEmitTime = System.currentTimeMillis().milliseconds

        while (true) {
            delay(interval)
            val currentTime = System.currentTimeMillis().milliseconds
            val elapsedTime = currentTime - lastEmitTime
            emit(elapsedTime)
            lastEmitTime = currentTime
        }
    }
}
