package com.example.historicalpetersburg.tools.settings

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.historicalpetersburg.tools.LocaleHelper
import java.util.Locale

class LanguageChain(private val localeHelper: LocaleHelper) : SettingsChainBase() {
    override val curKey: String = localeHelper.key

    override fun Handle(sharedPreferences: SharedPreferences, value: Any?): Boolean {
        var newValue: String? = null

        if (value != null) {
            newValue = value.toString()
        } else if (!sharedPreferences.contains(curKey)) {
            newValue = Locale.getDefault().language
        }

        if (newValue != null) {
            localeHelper.languageCode = newValue
            sharedPreferences.edit {
                putString(curKey, localeHelper.languageCode)
                this.apply()
            }
        }

        localeHelper.languageCode =
            sharedPreferences.getString(curKey, Locale.getDefault().language).toString()

        return true
    }
}