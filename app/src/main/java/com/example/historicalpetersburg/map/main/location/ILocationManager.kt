package com.example.historicalpetersburg.map.main.location

import com.example.historicalpetersburg.map.main.models.Coordinate


interface ILocationManager {
    val proxy: AvailableUseLocationProxy?

    val curPosition: Coordinate?

    var follow: Boolean
    var displayLocation: Boolean
    var actionsToFollowChange: MutableList<((Boolean) -> Unit)>
    var locationListeners: MutableList<LocationUpdateListener>

    fun startUpdate()
    fun stopUpdate()

    fun zoomInPosition(): Boolean
}