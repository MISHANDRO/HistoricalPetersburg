package com.example.historicalpetersburg.map.yandex.location

import android.animation.ValueAnimator
import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.main.models.Coordinate
import com.example.historicalpetersburg.map.main.location.LocationUpdateListener
import com.example.historicalpetersburg.map.main.models.AnimationZoomType
import com.example.historicalpetersburg.map.main.shape.style.PlacemarkStyle
import com.example.historicalpetersburg.map.yandex.YandexPlacemark
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.map.PlacemarkMapObject

class YandexLocationListener(
    private val listeners: MutableList<LocationUpdateListener>
) : LocationListener {

    private var curPositionMapObject: PlacemarkMapObject? = null
    private var animator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 600

        addUpdateListener { animation ->
            val fraction = animation.animatedValue as Float
            val lat = curPositionMapObject!!.geometry.latitude + (curPosition!!.latitude - curPositionMapObject!!.geometry.latitude) * fraction
            val lon = curPositionMapObject!!.geometry.longitude + (curPosition!!.longitude - curPositionMapObject!!.geometry.longitude) * fraction
            curPositionMapObject?.geometry = Point(lat, lon)
        }
    }

    var curPosition: Point? = null
        private set

    var followed: Boolean = false
    var displayLocation: Boolean = true
        set(value) {
            curPositionMapObject?.opacity = if (value) 1f else 0f
            field = value
        }

    override fun onLocationUpdated(location: Location) {
        if (curPosition == null) {
            curPositionMapObject = (Coordinate.fromYandexPoint(location.position)?.let {
                MapManager.instance.map.addPlacemark(it).apply {
                    style = PlacemarkStyle.Start1 // TODO
                }
            } as YandexPlacemark).placemarkObject
            curPosition = location.position
        }

        curPosition = location.position
        listeners.forEach { it.onLocationUpdated(Coordinate.fromYandexPoint(curPosition)!!) }

        animator.cancel()
        animator.start()

        if (followed) {
            zoomInPosition()
        }
    }

    override fun onLocationStatusUpdated(locationStatus: LocationStatus) {
        if (locationStatus == LocationStatus.NOT_AVAILABLE) {
            curPosition = null
            displayLocation = false
        } else {
            displayLocation = true
        }

        listeners.forEach { it.onLocationStatusUpdated(locationStatus != LocationStatus.NOT_AVAILABLE) }
    }

    fun zoomInPosition(): Boolean {
        if (curPosition == null) return false

        MapManager.instance.map.let {
            var zoomValue = it.camera.zoom
            if (zoomValue < 8) zoomValue = 16.5f

            it.zoom(Coordinate.fromYandexPoint(curPosition)!!, zoomValue, 0.1f, AnimationZoomType.LINEAR)
        }

        return true
    }
}