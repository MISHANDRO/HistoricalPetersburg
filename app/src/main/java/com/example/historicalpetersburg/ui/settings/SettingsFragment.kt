package com.example.historicalpetersburg.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.historicalpetersburg.App
import com.example.historicalpetersburg.R


class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var listener: SettingsChangedListener

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.sharedPreferencesName = "settings"
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        listener = SettingsChangedListener(requireActivity().application as App)
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences!!.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun onPause() {
        preferenceManager.sharedPreferences!!.unregisterOnSharedPreferenceChangeListener(listener)
        super.onPause()
    }
}