package com.example.historicalpetersburg.map.main.repositories

import com.example.historicalpetersburg.map.main.objects.PlaceData

interface IPlaceRepository {
    fun getAllData() : Array<PlaceData>
}