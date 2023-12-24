package com.example.historicalpetersburg.map.main.shape

import com.example.historicalpetersburg.map.main.models.Coordinate

interface IMapObject {
    var visibility: Boolean

    fun setAction(action: (Coordinate) -> Boolean)
}