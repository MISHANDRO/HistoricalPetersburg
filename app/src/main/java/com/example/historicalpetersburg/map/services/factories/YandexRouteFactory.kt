package com.example.historicalpetersburg.map.services.factories

import com.example.historicalpetersburg.map.entities.Route
import com.example.historicalpetersburg.map.models.Coordinate
import com.example.historicalpetersburg.map.models.line.YandexRouteLine

class YandexRouteFactory : IRouteFactory {
    override fun create(id: Int, coordinates: List<Coordinate>, groupIds: List<Int>): Route {
        return Route(id).apply {
            this.line = YandexRouteLine(this, coordinates)
            this.groupIds += groupIds
        }
    }
}