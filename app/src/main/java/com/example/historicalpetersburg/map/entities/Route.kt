package com.example.historicalpetersburg.map.entities

import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.models.Coordinate
import com.example.historicalpetersburg.map.models.mapobject.ILine
import com.example.historicalpetersburg.map.views.bottomsheet.RouteInfoContentBottomSheet

class Route(
    coordinates: List<Coordinate>
) : IHistoricalObject {
    override var name: String = ""
    override var shortDesc: String = ""
    override var longDesc: String = ""

    override val coordinates: List<Coordinate>
        get() = line.coordinates

    var line: ILine = MapManager.instance.map.addLine(coordinates)

    override val groups: MutableList<Group> = mutableListOf()
    override var imagesArrayId: Int = -1
    override var completed: Boolean = false

    init {
        line.setAction {
            select()
            true
        }
    }

    override fun select() {
        MapManager.instance.map.zoom(coordinates)
        MapManager.instance.objectManager.hideAll()
        show()

        RouteInfoContentBottomSheet(this).show()
    }

    override fun hide() {
        line.hide()
    }

    override fun show() {
        line.show()
    }
}
