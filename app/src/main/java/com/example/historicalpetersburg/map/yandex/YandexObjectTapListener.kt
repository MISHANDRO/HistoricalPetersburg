package com.example.historicalpetersburg.map.yandex

import com.example.historicalpetersburg.map.main.models.Coordinate
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectTapListener

class YandexObjectTapListener : MapObjectTapListener {

    var action: (Coordinate) -> Boolean = { true }

    override fun onMapObjectTap(mapObject: MapObject, point: Point): Boolean {
        return action.invoke(Coordinate.fromYandexPoint(point) ?: Coordinate(.0, .0))
    }
}