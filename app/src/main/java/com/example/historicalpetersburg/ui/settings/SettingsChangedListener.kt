package com.example.historicalpetersburg.ui.settings

import android.app.LocaleManager
import android.content.SharedPreferences
import android.os.LocaleList
import com.example.historicalpetersburg.App
import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.tools.settings.Settings
import java.util.Locale

class SettingsChangedListener(private val app: App) : SharedPreferences.OnSharedPreferenceChangeListener {
    private val sync = "QWE"

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (sharedPreferences == null || key == null) return

        Settings.instance.setValueByKey(sharedPreferences, key)
    }
}

//class SettingsChangedListener : Preference.OnPreferenceChangeListener {
//    val sync = "sync"
//
//    override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {
//        if ()
//    }
//}