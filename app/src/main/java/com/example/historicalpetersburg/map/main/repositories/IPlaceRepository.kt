package com.example.historicalpetersburg.map.main.repositories

import com.example.historicalpetersburg.map.main.Coordinate
import com.example.historicalpetersburg.map.main.objects.PlaceData
import com.example.historicalpetersburg.map.main.objects.RouteData

interface IPlaceRepository {
    fun getAllData() : Array<PlaceData>
}