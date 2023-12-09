package com.example.historicalpetersburg.tools.value

import com.example.historicalpetersburg.tools.GlobalTools

class StringVal(override var id: Int = 0) : IValue<String> {

    constructor(value: String) : this() {
        this.value = value
    }

    override var value: String = ""
        get() {
            if (id == 0) {
                return field
            }

            return GlobalTools.instance.getString(id)
        }
}