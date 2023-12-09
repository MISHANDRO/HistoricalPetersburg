package com.example.historicalpetersburg.map.main.shape

import com.example.historicalpetersburg.map.main.Coordinate
import com.example.historicalpetersburg.map.main.shape.style.RouteStyle

interface ILine : IMapObject {
    val coordinates: List<Coordinate>
    var style: RouteStyle
}