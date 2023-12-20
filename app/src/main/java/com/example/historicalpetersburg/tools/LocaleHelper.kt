package com.example.historicalpetersburg.tools

import android.content.Context
import com.example.historicalpetersburg.R
import java.util.Locale


class LocaleHelper {
    lateinit var availableLanguage: Array<String>
    var languageCode: String = "en"
        set(value) {
            field = if (availableLanguage.contains(value)) value else "en"
        }

    val key = "language"

    fun onAttach(context: Context): Context {
        return updateResources(context)
    }

    private fun updateResources(context: Context): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }

    @Suppress("deprecation")
    private fun updateResourcesLegacy(context: Context): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        configuration.setLayoutDirection(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }
}

