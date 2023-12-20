package com.example.historicalpetersburg.map.yandex.location

import com.example.historicalpetersburg.map.main.Coordinate
import com.example.historicalpetersburg.map.main.location.AvailableUseLocationProxy
import com.example.historicalpetersburg.map.main.location.ILocationManager
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.location.FilteringMode
import com.yandex.mapkit.location.LocationManager
import com.yandex.runtime.sensors.LocationActivityType

class YandexLocationManager(override val proxy: AvailableUseLocationProxy?) : ILocationManager {

    override var follow: Boolean = false
        set(value) {
            if (value == field) return

            if (value)
                proxy?.withAvailableUseLocation {
                    actionsToFollowChange.forEach { it.invoke(true) }
                }
            else  actionsToFollowChange.forEach { it.invoke(false) }


            locationListener.followed = value
            field = value
        }

    override var displayLocation: Boolean = true
        set(value) {
            locationListener.displayLocation = value
            field = value
        }

    override var actionsToFollowChange = mutableListOf<(Boolean) -> Unit>()

    override val curPosition: Coordinate?
        get() = Coordinate.fromYandexPoint(locationListener.curPosition)

    private val locationManager: LocationManager = MapKitFactory.getInstance().createLocationManager(
        LocationActivityType.PEDESTRIAN
    )
    private var locationListener = YandexLocationListener()

    override fun startUpdate() {
        proxy?.withAvailableUseLocation { }
        locationManager.subscribeForLocationUpdates(0.0, 1000, 0.0, false, FilteringMode.OFF, locationListener);
        locationManager.requestSingleUpdate(locationListener)
    }

    override fun stopUpdate() {
        locationManager.unsubscribe(locationListener)
    }

    override fun zoomInPosition() {
        locationListener.zoomInPosition()
    }
}