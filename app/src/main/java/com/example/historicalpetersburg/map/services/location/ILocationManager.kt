package com.example.historicalpetersburg.map.services.location

import com.example.historicalpetersburg.map.models.Coordinate


interface ILocationManager {
    val locationPermissionRequestCode: Int get() = 1
    val requestCheckSettings: Int get() = 1000

    val curPosition: Coordinate?

    fun subscribeToLocationUpdate()
    fun unsubscribeToLocationUpdate()

    fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray)
    fun onLocationEnabledResult(requestCode: Int, resultCode: Int)

    fun displayLocation()
    fun hideLocation()

    fun follow()
    fun notFollow()
}