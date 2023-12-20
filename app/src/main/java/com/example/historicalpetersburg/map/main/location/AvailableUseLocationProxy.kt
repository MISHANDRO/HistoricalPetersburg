package com.example.historicalpetersburg.map.main.location

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.widget.Button
import android.widget.FrameLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.tools.GlobalTools
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.SettingsClient
import com.google.android.material.bottomsheet.BottomSheetDialog


class AvailableUseLocationProxy {
    private val locationPermissionRequestCode: Int get() = 1
    private val requestCheckSettings: Int get() = 1000

    private var locationRequestResultCallback: (() -> Unit)? = null

    fun withAvailableUseLocation(action: () -> Unit) {
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

    fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
        if (requestCode == locationPermissionRequestCode && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationRequestResultCallback?.let { withAvailableUseLocation(it) }
            } else {
                alertDialogToRequestPermission()
            }
        }

        locationRequestResultCallback = null
    }

    fun onLocationEnabledResult(requestCode: Int, resultCode: Int) {
        if (requestCode == requestCheckSettings) {
            if (resultCode == Activity.RESULT_OK) {
                locationRequestResultCallback?.let { withAvailableUseLocation(it) }
            } else {
                GlobalTools.instance.toast("Твои проблемы TODO")
            }
        }

        locationRequestResultCallback = null
    }

    private fun alertDialogToRequestPermission() {

        val bottomSheetDialog = BottomSheetDialog(GlobalTools.instance.activity, R.style.MainBottomSheetDialog)
        bottomSheetDialog.setContentView(R.layout.info_access_location)

        bottomSheetDialog.findViewById<Button>(R.id.go_to_settings)?.setOnClickListener {
            bottomSheetDialog.dismiss()

            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", GlobalTools.instance.activity.packageName, null)
            intent.data = uri
            GlobalTools.instance.activity.startActivity(intent)
        }

        bottomSheetDialog.show()
    }
}
