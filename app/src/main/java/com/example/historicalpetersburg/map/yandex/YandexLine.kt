package com.example.historicalpetersburg.map.yandex

import com.example.historicalpetersburg.map.main.shape.ILine
import com.example.historicalpetersburg.map.main.models.Coordinate
import com.example.historicalpetersburg.map.main.shape.style.RouteStyle
import com.yandex.mapkit.map.PolylineMapObject

class YandexLine(
    override val coordinates: List<Coordinate>,
    style: RouteStyle = RouteStyle.Default
) : ILine {
    var polylineObject: PolylineMapObject? = null
        set(value) {
            field = value
            field?.addTapListener(listener)
            style = style
        }

    override var visibility: Boolean = true
        set(value) {
            field = value
            polylineObject?.isVisible = field
        }

    private val listener = YandexObjectTapListener()

    override var style: RouteStyle = style
        set(value) {
            field = value
            polylineObject?.apply {
                strokeWidth = value.strokeWidth
                setStrokeColor(value.strokeColor)
                outlineWidth = value.outlineWidth
                outlineColor = value.outlineColor
            }
        }

    init {
        this.style = style
    }

    override fun setAction(action: (Coordinate) -> Boolean) {
        listener.action = action
    }
}