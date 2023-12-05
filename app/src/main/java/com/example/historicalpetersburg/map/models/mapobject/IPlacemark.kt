package com.example.historicalpetersburg.map.models.mapobject

import com.example.historicalpetersburg.map.models.Coordinate
import com.example.historicalpetersburg.map.models.mapobject.objectstyles.PlacemarkStyle

interface IPlacemark : IMapObject {
    val coordinate: Coordinate
    var style: PlacemarkStyle
}