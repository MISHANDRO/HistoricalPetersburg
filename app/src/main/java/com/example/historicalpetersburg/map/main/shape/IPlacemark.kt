package com.example.historicalpetersburg.map.main.shape

import com.example.historicalpetersburg.map.main.models.Coordinate
import com.example.historicalpetersburg.map.main.shape.style.PlacemarkStyle

interface IPlacemark : IMapObject {
    val coordinate: Coordinate
    var style: PlacemarkStyle

    var direction: Float
}