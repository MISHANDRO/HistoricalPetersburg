package com.example.historicalpetersburg.map.services.location

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.historicalpetersburg.tools.GlobalTools
import com.example.historicalpetersburg.map.main.Coordinate
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.SettingsClient

class AvailableUseLocationProxy(private val child: ILocationManager) : ILocationManager  {
    private var locationRequestResultCallback: (() -> Unit)? = null

    override val curPosition: Coordinate?
        get() = child.curPosition

    override fun subscribeToLocationUpdate() {
        withAvailableUseLocation { child.subscribeToLocationUpdate() }
    }

    override fun unsubscribeToLocationUpdate() {
        child.unsubscribeToLocationUpdate()
    }

    private fun withAvailableUseLocation(action: () -> Unit) {
        if (!haveLocationPermission()) {
            locationRequestResultCallback = action
            requestPermission()
            return
        }

        if (!isLocationEnabled()) {
            locationRequestResultCallback = action
            requestLocationEnabled()
            return
        }

        action.invoke()
        locationRequestResultCallback = null
    }


    private fun haveLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            GlobalTools.instance.activity,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        val canRequestPermission: Boolean = ActivityCompat.shouldShowRequestPermissionRationale(
            GlobalTools.instance.activity, android.Manifest.permission.ACCESS_FINE_LOCATION)

        if (canRequestPermission) {
            ActivityCompat.requestPermissions(
                GlobalTools.instance.activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionRequestCode
            )
        } else {
            locationRequestResultCallback = null
            alertDialogToRequestPermission()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = GlobalTools.instance.activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun requestLocationEnabled() {
        val locationSettingsRequest = LocationSettingsRequest.Builder().addLocationRequest(
            LocationRequest.create()).build()

        val settingsClient: SettingsClient = LocationServices.getSettingsClient(GlobalTools.instance.activity)

        settingsClient.checkLocationSettings(locationSettingsRequest)
            .addOnCompleteListener(GlobalTools.instance.activity) { task ->
                try {
                    task.getResult(ApiException::class.java)
                } catch (ex: ApiException) {
                    when (ex.statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                            try {
                                val resolvableApiException = ex as ResolvableApiException
                                resolvableApiException.startResolutionForResult(
                                    GlobalTools.instance.activity,
                                    requestCheckSettings
                                )
                            } catch (_: IntentSender.SendIntentException) { }
                        }
                    }
                }
            }
    }

    override fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
        if (requestCode == locationPermissionRequestCode && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationRequestResultCallback?.let { withAvailableUseLocation(it) }
            } else {
                alertDialogToRequestPermission()
            }
        }

        locationRequestResultCallback = null
    }

    override fun onLocationEnabledResult(requestCode: Int, resultCode: Int) {
        if (requestCode == requestCheckSettings) {
            if (resultCode == Activity.RESULT_OK) {
                locationRequestResultCallback?.let { withAvailableUseLocation(it) }
            } else {
                GlobalTools.instance.toast("Твои проблемы TODO")
            }
        }

        locationRequestResultCallback = null
    }

    override fun displayLocation() {
        child.displayLocation()
    }

    override fun hideLocation() {
        child.hideLocation()
    }

    override fun follow() {
        withAvailableUseLocation { child.follow() }
    }

    override fun notFollow() {
        child.notFollow()
    }


    private fun alertDialogToRequestPermission() { // TODO не алерт а окно
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(GlobalTools.instance.activity)
        alertDialogBuilder.setTitle("Нужен доступ к уведомлениям")
        alertDialogBuilder.setMessage("Это приложение требует доступа к уведомлениям для корректной работы.")

        alertDialogBuilder.setPositiveButton("Настройки") { _, _ ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", GlobalTools.instance.activity.packageName, null)
            intent.data = uri
            GlobalTools.instance.activity.startActivity(intent)
        }

        alertDialogBuilder.setNegativeButton("Отмена") { _, _ ->
            GlobalTools.instance.toast("Ну и иди нахуй, еблана кусок TODO")
        }
        alertDialogBuilder.show()
    }
}
