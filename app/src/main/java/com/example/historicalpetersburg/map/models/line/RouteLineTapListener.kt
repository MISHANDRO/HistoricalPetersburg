package com.example.historicalpetersburg.map.models.line

import com.example.historicalpetersburg.map.entities.Route
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectTapListener

class RouteLineTapListener(private val route: Route) : MapObjectTapListener {
    override fun onMapObjectTap(p0: MapObject, p1: com.yandex.mapkit.geometry.Point): Boolean {
        route.select()

        return true
    }
}