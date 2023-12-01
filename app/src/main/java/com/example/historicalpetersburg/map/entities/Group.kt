package com.example.historicalpetersburg.map.entities

import com.example.historicalpetersburg.map.models.Coordinate

class Group(val id: Int) {
    var name: String = ""
    var shortDesc: String = ""
    var longDesc: String = ""

    fun select(zoomPaddingX: Float, zoomPaddingY: Float, action: (() -> Unit)?) {
        TODO("Not yet implemented")
    }

    val routes: MutableList<Route> = mutableListOf()

    val coordinates: List<Coordinate>
        get() {
            val result = mutableListOf<Coordinate>()
            for (route in routes) {
                result += route.coordinates
            }

            return result
        }

    fun hide() {
        routes.forEach { it.line.hide() }
    }

    fun show() {
        routes.forEach { it.line.show() }
    }
}