package com.example.historicalpetersburg.map.entities

import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.models.Coordinate
import com.example.historicalpetersburg.map.models.line.IRouteLine
import com.example.historicalpetersburg.map.views.RouteInfoDialog

class Route(val id: Int) : IHistoricalObject {
    override var name: String = ""
    override var shortDesc: String = ""
    override var longDesc: String = ""

    lateinit var line: IRouteLine

    val groupIds = mutableListOf<Int>()

    val coordinates: List<Coordinate>
        get() = line.coordinates

    override fun select(zoomPaddingX: Float,
                        zoomPaddingY: Float,
                        action: (() -> Unit)?) {
        MapManager.instance.map.zoom(coordinates)
        MapManager.instance.routeManager.hideAll()
        show()

        action?.invoke()

        val dialog = RouteInfoDialog(this)
        dialog.show(MapManager.instance.fragmentManager, dialog.tag)
    }

    override fun hide() {
        line.hide()
    }

    override fun show() {
        line.show()
    }
}
