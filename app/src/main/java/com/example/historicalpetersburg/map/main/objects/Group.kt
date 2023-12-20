package com.example.historicalpetersburg.map.main.objects

import com.example.historicalpetersburg.map.main.Coordinate
import com.example.historicalpetersburg.tools.value.Value

class Group(
    val id: Int,
    val name: Value<String>
) {

    val historicalObjects: MutableList<IHistoricalObjectData> = mutableListOf()

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