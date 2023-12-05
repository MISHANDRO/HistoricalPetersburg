package com.example.historicalpetersburg.map.models.mapobject

import com.example.historicalpetersburg.map.models.Coordinate

interface IMapObject {
    fun setAction(action: (Coordinate) -> Boolean)

    fun show()
    fun hide()
}