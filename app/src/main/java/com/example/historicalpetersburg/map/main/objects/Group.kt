package com.example.historicalpetersburg.map.main.objects

import com.example.historicalpetersburg.map.main.Coordinate
import com.example.historicalpetersburg.tools.value.IValue
import com.example.historicalpetersburg.tools.value.StringVal

class Group {


    var id: Int = -1

    var name: IValue<String> = StringVal()
    var shortDesc: IValue<String> = StringVal()
    var longDesc: IValue<String> = StringVal()

    val historicalObjects: MutableList<IHistoricalObject> = mutableListOf()

    val coordinates: List<Coordinate>
        get() {
            val result = mutableListOf<Coordinate>()
            for (route in historicalObjects) {
                result += route.coordinates
            }

            return result
        }

    fun hide() {
        historicalObjects.forEach { it.hide() }
    }

    fun show() {
        historicalObjects.forEach { it.show() }
    }
}