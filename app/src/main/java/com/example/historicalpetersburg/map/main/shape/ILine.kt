package com.example.historicalpetersburg.map.main.shape

import com.example.historicalpetersburg.map.main.models.Coordinate
import com.example.historicalpetersburg.map.main.shape.style.RouteStyle

interface ILine : IMapObject {
    val coordinates: List<Coordinate>
    var style: RouteStyle
}