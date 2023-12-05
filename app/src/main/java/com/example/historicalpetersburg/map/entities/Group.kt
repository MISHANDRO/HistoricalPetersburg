package com.example.historicalpetersburg.map.entities

import com.example.historicalpetersburg.map.models.Coordinate

class Group {

    companion object {
        var count = 0
    }

    val id: Int = count

    var name: String = ""
    var shortDesc: String = ""
    var longDesc: String = ""

    val historicalObjects: MutableList<IHistoricalObject> = mutableListOf()

    val coordinates: List<Coordinate>
        get() {
            val result = mutableListOf<Coordinate>()
            for (route in historicalObjects) {
                result += route.coordinates
            }

            return result
        }

    init {
        ++count;
    }

    fun hide() {
        historicalObjects.forEach { it.hide() }
    }

    fun show() {
        historicalObjects.forEach { it.show() }
    }
}