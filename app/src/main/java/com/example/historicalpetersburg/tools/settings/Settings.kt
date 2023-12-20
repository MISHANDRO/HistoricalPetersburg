package com.example.historicalpetersburg.tools.settings

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.historicalpetersburg.tools.LocaleHelper

class Settings private constructor() {

    companion object {
        private val instanceObj: Settings by lazy { Settings() }
        const val defaultName = "settings"

        val instance: Settings
            get() {
                return instanceObj
            }

    }

    val localeHelper = LocaleHelper()
    private val settingsChain: ISettingsChain

    init {
        settingsChain = LanguageChain(localeHelper)
    }

    fun getDefaultSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(defaultName, MODE_PRIVATE)
    }

    fun setDefaultValueByKey(context: Context, key: String, value: Any? = null) : Boolean {
        return setValueByKey(getDefaultSharedPreferences(context), key, value)
    }

    fun setValueByKey(sharedPreferences: SharedPreferences, key: String, value: Any? = null) : Boolean {
        return settingsChain.setValue(sharedPreferences, key, value)
    }
}
