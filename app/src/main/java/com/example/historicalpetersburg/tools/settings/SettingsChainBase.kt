package com.example.historicalpetersburg.tools.settings

import android.content.SharedPreferences

abstract class SettingsChainBase : ISettingsChain {

    private var _next: ISettingsChain? = null
    abstract val curKey: String

    override fun addNext(next: ISettingsChain) {
        if (_next == null) {
            _next = next
        } else {
            _next!!.addNext(next)
        }
    }

    override fun setValue(sharedPreferences: SharedPreferences, key: String, value: Any?): Boolean {
        if (curKey == key) {
            return Handle(sharedPreferences, value)
        } else if (_next != null) {
            return _next!!.setValue(sharedPreferences, key, value)
        }

        return false
    }

    abstract fun Handle(sharedPreferences: SharedPreferences, value: Any?): Boolean
}