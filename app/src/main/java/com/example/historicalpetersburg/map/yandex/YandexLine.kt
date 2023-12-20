package com.example.historicalpetersburg.map.yandex

import com.example.historicalpetersburg.map.main.shape.ILine
import com.example.historicalpetersburg.map.main.Coordinate
import com.example.historicalpetersburg.map.main.shape.style.RouteStyle
import com.yandex.mapkit.map.PolylineMapObject

class YandexLine(
    private val polylineObject : PolylineMapObject,
    override val coordinates: List<Coordinate>,
    style: RouteStyle = RouteStyle.Default
) : ILine {

    override var visibility: Boolean = true
        set(value) {
            field = value
            polylineObject.isVisible = field
        }

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
}