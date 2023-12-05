package com.example.historicalpetersburg.map.services.location

import com.example.historicalpetersburg.GlobalTools
import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.models.Coordinate
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.map.PlacemarkMapObject

class YandexLocationListener : LocationListener {

    var curPosition: Point? = null
        private set

    var followed: Boolean = false

    private var curPositionMapObject: PlacemarkMapObject? = null

    override fun onLocationUpdated(location: Location) {
        if (curPosition == null) {
            curPositionMapObject = Coordinate.fromYandexPoint(location.position)?.let {
                MapManager.instance.map.addPlacemark(it)
            } as PlacemarkMapObject
        }

        if (followed) {
            MapManager.instance.map.zoom(Coordinate.fromYandexPoint(location.position)!!, 17f, 0.1f)
        }

        curPosition = location.position
        curPositionMapObject?.geometry = location.position

//        if (location.heading != null) {
//            curPositionMapObject.direction = location.heading!!.toFloat()
//        }

    //        Log.w(
//            MainActivity::class.java.simpleName,
//            "my location - " + curPosition!!.latitude + "," + curPosition!!.longitude
//        )
    }

    override fun onLocationStatusUpdated(locationStatus: LocationStatus) {
        if (locationStatus == LocationStatus.NOT_AVAILABLE) {
            curPosition = null
            hidePosition()
            GlobalTools.instance.toast("Не доступно")
        } else {
            displayPosition()
            GlobalTools.instance.toast("Доступно")
        }
    }

    fun displayPosition() {
        curPositionMapObject?.opacity = 0f
    }

    fun hidePosition() {
        curPositionMapObject?.opacity = 1f
    }
}