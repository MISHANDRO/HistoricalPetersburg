package com.example.historicalpetersburg.map.main.shape

import com.example.historicalpetersburg.map.main.Coordinate
import com.example.historicalpetersburg.map.main.shape.style.PlacemarkStyle

interface IPlacemark : IMapObject {
    val coordinate: Coordinate
    var style: PlacemarkStyle
}