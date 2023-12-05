package com.example.historicalpetersburg.map.models.mapobject

import com.example.historicalpetersburg.map.models.Coordinate
import com.example.historicalpetersburg.map.models.mapobject.objectstyles.RouteStyle
import com.yandex.mapkit.map.PolylineMapObject

class YandexLine(
    private val polylineObject : PolylineMapObject,
    override val coordinates: List<Coordinate>,
    style: RouteStyle = RouteStyle.Default
) : ILine {

    private val listener = YandexObjectTapListener()

    override var style: RouteStyle = style
        set(value) {
            field = value
            polylineObject.apply {
                strokeWidth = value.strokeWidth
                setStrokeColor(value.strokeColor)
                outlineWidth = value.outlineWidth
                outlineColor = value.outlineColor
            }
        }

    init {
        polylineObject.addTapListener(listener)
        this.style = style
    }

    override fun setAction(action: (Coordinate) -> Boolean) {
        listener.action = action
    }

    override fun show() {
        polylineObject.isVisible = true
    }

    override fun hide() {
        polylineObject.isVisible = false
    }
}