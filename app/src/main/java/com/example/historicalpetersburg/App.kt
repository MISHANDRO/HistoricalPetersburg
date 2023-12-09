package com.example.historicalpetersburg

import android.app.Application
import android.content.res.Configuration
import com.example.historicalpetersburg.activities.MainActivity
import com.example.historicalpetersburg.map.MapManager
import java.util.Locale


class App : Application() {
    var mainActivity: MainActivity? = null

    override fun onCreate() {
        super.onCreate()
        MapManager.setYandexApiKey("cda8bfdd-d4b6-4c01-98b7-9207602e9f3b")

        setLang(getSharedPreferences("settings", MODE_PRIVATE)?.getString("lang", "en"))
//        setLocale()
    }

    fun setLang(lang: String?) {
        val resources = resources
        val configuration: Configuration = resources.configuration
        val locale: Locale = Locale(lang ?: "en")
        if (!configuration.locale.equals(locale)) {
            configuration.setLocale(locale)
            resources.updateConfiguration(configuration, null)
        }

        mainActivity?.recreate()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
//        setLocale()
    }

    private fun setLocale() {
        val resources = resources
        val configuration: Configuration = resources.configuration
        val locale: Locale = Locale("en")
        if (!configuration.locale.equals(locale)) {
            configuration.setLocale(locale)
            resources.updateConfiguration(configuration, null)
        }

    }
}