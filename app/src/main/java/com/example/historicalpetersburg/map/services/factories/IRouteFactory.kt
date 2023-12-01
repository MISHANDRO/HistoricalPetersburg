package com.example.historicalpetersburg.map.services.factories

import com.example.historicalpetersburg.map.entities.Route
import com.example.historicalpetersburg.map.models.Coordinate

interface IRouteFactory {
    fun create(id: Int, coordinates: List<Coordinate>, groupIds: List<Int>): Route
}