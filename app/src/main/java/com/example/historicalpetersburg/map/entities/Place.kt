package com.example.historicalpetersburg.map.entities

import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.models.Coordinate
import com.example.historicalpetersburg.map.models.mapobject.IPlacemark

class Place(
    coordinate: Coordinate,
) : IHistoricalObject {
    override var name: String = ""
    override var shortDesc: String = ""
    override var longDesc: String = ""

    override val coordinates: List<Coordinate>
        get() = listOf(placemark.coordinate)

    var placemark: IPlacemark = MapManager.instance.map.addPlacemark(coordinate)

    override val groups: MutableList<Group> = mutableListOf()
    override var imagesArrayId: Int = -1
    override var completed: Boolean = false

    init {
        placemark.setAction {
//            select()
            println("select")
            true
        }
    }

    override fun select() {
        TODO("Not yet implemented")
    }

    override fun show() {
        placemark.show()
    }

    override fun hide() {
        placemark.hide()
    }
}