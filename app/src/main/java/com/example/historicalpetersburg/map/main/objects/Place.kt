package com.example.historicalpetersburg.map.main.objects

import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.main.Coordinate
import com.example.historicalpetersburg.map.main.shape.IPlacemark
import com.example.historicalpetersburg.tools.value.IValue
import com.example.historicalpetersburg.tools.value.StringVal

class Place(
    coordinate: Coordinate,
) : IHistoricalObject {
    override var name: IValue<String> = StringVal()
    override var shortDesc: IValue<String> = StringVal()
    override var longDesc: IValue<String> = StringVal()

    override val coordinates: List<Coordinate>
        get() = listOf(placemark.coordinate)

    var placemark: IPlacemark = MapManager.instance.map.addPlacemark(coordinate)

    override val groups: MutableList<Group> = mutableListOf()
    override var imagesArrayId: Int = -1

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