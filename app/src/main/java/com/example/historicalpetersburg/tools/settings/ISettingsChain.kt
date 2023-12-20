package com.example.historicalpetersburg.tools.settings

import android.content.SharedPreferences

interface ISettingsChain {
    fun addNext(next: ISettingsChain)
    fun setValue(sharedPreferences: SharedPreferences, key: String, value: Any?): Boolean
}