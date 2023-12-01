package com.example.historicalpetersburg.map.models.line

import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.entities.Route
import com.example.historicalpetersburg.map.models.Coordinate
import com.example.historicalpetersburg.map.services.routeManagers.RouteManager
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PolylineMapObject

class YandexRouteLine(
    route: Route,
    override val coordinates: List<Coordinate>) : IRouteLine {

    private val polylineObject: PolylineMapObject = MapManager.instance.map.addLine(coordinates) as PolylineMapObject
    private val listener: MapObjectTapListener = RouteLineTapListener(route) // TODO

    init {
        polylineObject.addTapListener(listener)
        // TODO нажатие и внешний вид
    }

    override fun show() {
        polylineObject.isVisible = true
    }

    override fun hide() {
        polylineObject.isVisible = false
    }
}