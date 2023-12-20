package com.example.historicalpetersburg.tools.value

import com.example.historicalpetersburg.tools.GlobalTools

class StringVal : Value<String>() {
    var id = -1

    override var value: String = ""
        get() {
            if (id == -1)
                return field

            return GlobalTools.instance.getString(id)
        }
}