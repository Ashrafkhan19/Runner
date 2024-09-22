package com.example.run.domain

import com.example.core.domain.location.LocationTimeStamp
import kotlin.math.roundToInt

object LocationDataCalculator {

    fun getTotalDistanceMeters(location: List<List<LocationTimeStamp>>): Int {
        return location.sumOf { timestampsPerLine ->
            timestampsPerLine.zipWithNext { location1, location2 ->
                location1.location.location.distanceTo(location2.location.location)
            }.sum().roundToInt()
        }
    }
}