package com.example.historicalpetersburg.map.main.repositories

import com.example.historicalpetersburg.map.main.objects.PartRoute
import com.example.historicalpetersburg.map.main.objects.PlaceData
import com.example.historicalpetersburg.map.main.objects.RouteData

interface IUserRepository {
    fun getCompletePartsByRoute(routeData: RouteData): Array<Int>
    fun getCompletePartsByPlace(placeData: PlaceData): Array<Int>
    fun isPlaceCompleted(placeData: PlaceData): Boolean

    fun saveCompletePart(partRoute: PartRoute, routeId: Int)
    fun saveCompletePlace(placeData: PlaceData)

    fun resetAll()
}