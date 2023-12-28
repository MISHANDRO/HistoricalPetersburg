package com.example.historicalpetersburg

import android.app.Activity
import android.app.Application
import android.net.ConnectivityManager
import android.os.Bundle
import com.example.historicalpetersburg.activities.MainActivity
import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.tools.NetworkStateHandler
import com.example.historicalpetersburg.tools.settings.Settings


class App : Application() {
    private val activeActivitiesMutable = mutableListOf<Activity>()
    var mainActivity: MainActivity? = null

    val activeActivities: List<Activity>
        get() = activeActivitiesMutable

    override fun onCreate() {
        super.onCreate()
        MapManager.setYandexApiKey("cda8bfdd-d4b6-4c01-98b7-9207602e9f3b")

        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(NetworkStateHandler(this))

        Settings.instance.localeHelper.availableLanguage = resources.getStringArray(R.array.lang_values)
        val defaultPreference = Settings.instance.getDefaultSharedPreferences(this)
        Settings.instance.setValueByKey(defaultPreference, "language")

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activeActivitiesMutable.add(activity)
            }

            override fun onActivityStarted(activity: Activity) {}

            override fun onActivityResumed(activity: Activity) {}

            override fun onActivityPaused(activity: Activity) {}

            override fun onActivityStopped(activity: Activity) {}

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

            override fun onActivityDestroyed(activity: Activity) {
                activeActivitiesMutable.remove(activity)
            }
        })
    }


}