package com.example.historicalpetersburg.map.main.objects

import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.main.Coordinate
import com.example.historicalpetersburg.map.main.shape.ILine
import com.example.historicalpetersburg.map.main.views.bottomsheet.RouteInfoContentBottomSheet
import com.example.historicalpetersburg.tools.value.IValue
import com.example.historicalpetersburg.tools.value.StringVal
import com.google.android.material.bottomsheet.BottomSheetBehavior

class Route(
    coordinates: List<Coordinate>
) : IHistoricalObject {
    override var name: IValue<String> = StringVal()
    override var shortDesc: IValue<String> = StringVal()
    override var longDesc: IValue<String> = StringVal()

    override val coordinates: List<Coordinate>
        get() = line.coordinates

    val line: ILine = MapManager.instance.map.addLine(coordinates)

    override val groups: MutableList<Group> = mutableListOf()
    override var imagesArrayId: Int = -1

    init {
        line.setAction {
            select()
            true
        }
    }

    override fun select() { // TODO отбить гвозди
        MapManager.instance.locationManager.follow = false

        MapManager.instance.map.zoom(coordinates)
        MapManager.instance.objectManager.hideAll()
        show()

        MapManager.instance.mapFragment.bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED

        RouteInfoContentBottomSheet(this).show()
    }

    override fun hide() {
        line.hide()
    }

    override fun show() {
        line.show()
    }
}
