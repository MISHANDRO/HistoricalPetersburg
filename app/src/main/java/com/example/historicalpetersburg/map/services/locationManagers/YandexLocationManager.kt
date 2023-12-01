package com.example.historicalpetersburg.map.services.locationManagers

import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.models.Coordinate
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.location.FilteringMode
import com.yandex.mapkit.location.LocationManager
import com.yandex.runtime.sensors.LocationActivityType

class YandexLocationManager : ILocationManager {

    override val curPosition: Coordinate?
        get() = Coordinate.fromYandexPoint(locationListener.curPosition)

    private val locationManager: LocationManager = MapKitFactory.getInstance().createLocationManager(
        LocationActivityType.PEDESTRIAN
    )
    private var locationListener = YandexLocationListener()

    override fun subscribeToLocationUpdate() {
        locationManager.subscribeForLocationUpdates(0.0, 1000, 0.0, false, FilteringMode.OFF, locationListener);
        locationManager.requestSingleUpdate(locationListener)

        MapManager.instance.toast("Заебись работаем")
    }

    override fun unsubscribeToLocationUpdate() {
        locationManager.unsubscribe(locationListener)

        MapManager.instance.toast("Больше не работаем")
    }

    override fun displayLocation() {
        locationListener.displayPosition()
    }

    override fun hideLocation() {
        locationListener.hidePosition()
    }

    override fun follow() { // TODO
        locationListener.followed = !locationListener.followed
    }

    override fun notFollow() {
        locationListener.followed = false
    }

    override fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) { }
    override fun onLocationEnabledResult(requestCode: Int, resultCode: Int) { }
}