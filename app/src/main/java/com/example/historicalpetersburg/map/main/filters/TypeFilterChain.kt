package com.example.historicalpetersburg.map.main.filters

import com.example.historicalpetersburg.map.main.objects.IHistoricalObject
import java.lang.reflect.Type

class TypeFilterChain : HistoricalObjectFilterChainBase() {
    var type: Type? = null

    override fun Handle(historicalObject: IHistoricalObject): Boolean {
        if (type == null) {
            return true
        }

        return (historicalObject::class.java == type)
    }
}