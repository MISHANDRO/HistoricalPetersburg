package com.example.historicalpetersburg.map.main.repositories

import com.example.historicalpetersburg.map.main.objects.PartRoute
import com.example.historicalpetersburg.map.main.objects.RouteData

interface IRouteRepository {
    fun getAllData() : Array<RouteData>
    fun getFirstPartByRoute(route: RouteData): PartRoute?
    fun getAllPartIdsByRoute(route: RouteData): Array<Int>
    fun getPartById(partRouteId: Int): PartRoute?
}