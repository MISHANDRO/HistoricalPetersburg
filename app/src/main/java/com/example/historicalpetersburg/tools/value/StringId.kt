package com.example.historicalpetersburg.tools.value

import com.example.historicalpetersburg.tools.GlobalTools

class StringId(var id: Int = 0) : Value<String>() {

    constructor(id: String) : this(GlobalTools.instance.getIdentifier(id, "string"))

    override var value: String = ""
        get() = GlobalTools.instance.getString(id)
}