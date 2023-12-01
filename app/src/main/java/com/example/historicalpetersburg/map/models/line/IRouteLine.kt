package com.example.historicalpetersburg.map.models.line

import com.example.historicalpetersburg.map.models.Coordinate

interface IRouteLine {
    val coordinates: List<Coordinate>

    fun show()
    fun hide()
}