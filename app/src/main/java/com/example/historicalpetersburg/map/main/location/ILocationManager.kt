package com.example.historicalpetersburg.map.main.location

import com.example.historicalpetersburg.map.main.Coordinate


interface ILocationManager {
    val proxy: AvailableUseLocationProxy?

    val curPosition: Coordinate?

    var follow: Boolean
    var displayLocation: Boolean
    var actionsToFollowChange: MutableList<((Boolean) -> Unit)>

    fun startUpdate()
    fun stopUpdate()

    fun zoomInPosition()
}