package com.example.historicalpetersburg.map.models

import com.yandex.mapkit.geometry.Point

class Coordinate(var latitude: Double, var longitude: Double) {
    companion object {
        fun fromYandexPoint(point: Point?): Coordinate? {
            return if (point == null) null else Coordinate(point.latitude, point.longitude)
        }
    }

    fun toYandexPoint() : Point {
        return Point(latitude, longitude)
    }
}