package com.example.historicalpetersburg.map.main.location

import com.example.historicalpetersburg.map.main.models.Coordinate

abstract class LocationUpdateListener {
    open fun onLocationUpdated(coordinate: Coordinate) {}
    open fun onLocationStatusUpdated(isAvailable: Boolean) {}
}