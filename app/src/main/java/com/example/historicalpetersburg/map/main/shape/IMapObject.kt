package com.example.historicalpetersburg.map.main.shape

import com.example.historicalpetersburg.map.main.Coordinate

interface IMapObject {
    fun setAction(action: (Coordinate) -> Boolean)

    fun show()
    fun hide()
}