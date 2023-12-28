package com.example.historicalpetersburg.map.main.models

import com.yandex.mapkit.geometry.Point
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class Coordinate(var latitude: Double, var longitude: Double) {
    companion object {
        fun fromYandexPoint(point: Point?): Coordinate? {
            return if (point == null) null else Coordinate(point.latitude, point.longitude)
        }

        fun getDistance(coordinate1: Coordinate, coordinate2: Coordinate): Int {
            val earthRadius = 6371e3

            val lat1Rad = Math.toRadians(coordinate1.latitude)
            val lat2Rad = Math.toRadians(coordinate2.latitude)
            val deltaLat = Math.toRadians(coordinate2.latitude - coordinate1.latitude)
            val deltaLon = Math.toRadians(coordinate2.longitude - coordinate1.longitude)

            val a = sin(deltaLat / 2).pow(2) + cos(lat1Rad) * cos(lat2Rad) * sin(deltaLon / 2).pow(2)
            val c = 2 * atan2(sqrt(a), sqrt(1 - a))

            return (earthRadius * c).toInt()
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