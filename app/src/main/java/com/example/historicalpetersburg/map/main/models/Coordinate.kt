package com.example.historicalpetersburg.map.main.models

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

    override fun equals(other: Any?): Boolean {
        if (other !is Coordinate) return false
        return latitude.isApproximatelyEqual(other.latitude) && longitude.isApproximatelyEqual(other.longitude)
    }

    private fun Double.isApproximatelyEqual(other: Double, epsilon: Double = 1e-5): Boolean {
        return kotlin.math.abs(this - other) < epsilon
    }
}