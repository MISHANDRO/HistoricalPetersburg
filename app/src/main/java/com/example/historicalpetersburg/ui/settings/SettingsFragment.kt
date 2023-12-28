package com.example.historicalpetersburg.ui.settings

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.historicalpetersburg.App
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.activities.LanguageActivity
import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.tools.settings.Settings


class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var listener: SettingsChangedListener

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.sharedPreferencesName = Settings.defaultName
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        listener = SettingsChangedListener(requireActivity().application as App)

        findPreference<Preference>(Settings.instance.localeHelper.key)?.setOnPreferenceClickListener {
            val intent = Intent(activity, LanguageActivity::class.java)
            startActivity(intent)
            true
        }

        findPreference<Preference>("user_reset")?.setOnPreferenceClickListener {
            MapManager.instance.userManager.repository.resetAll()
            Toast.makeText(requireContext(), resources.getString(R.string.user_reset_success), Toast.LENGTH_SHORT).show()
            true
        }

        var summary = ""
        Settings.instance.localeHelper.let {
            summary = requireActivity().resources
                .getStringArray(R.array.lang_entries)[it.availableLanguage.indexOf(it.languageCode)]
        }
        findPreference<Preference>(Settings.instance.localeHelper.key)?.summary = summary
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