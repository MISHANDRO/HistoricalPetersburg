package com.example.historicalpetersburg.map.models.mapobject

import com.example.historicalpetersburg.map.models.Coordinate
import com.example.historicalpetersburg.map.models.mapobject.objectstyles.RouteStyle

interface ILine : IMapObject {
    val coordinates: List<Coordinate>
    var style: RouteStyle
}