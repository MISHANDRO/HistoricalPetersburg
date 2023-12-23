package com.example.historicalpetersburg.map.services.route

import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.main.objects.PartRoute
import com.example.historicalpetersburg.map.main.objects.RouteData

class PartState(
    val curPartRoute: PartRoute
) : IRouteInspectorState {
    override fun moveToStart(route: RouteData): IRouteInspectorState {
        return moveToInactive()
    }

    override fun moveToNextPart(partRouteId: Int): IRouteInspectorState {
//        TODO("сохраняем")
        val nextPartId = curPartRoute.nextId
        if (nextPartId == null) {
            return InactiveState()
        }

        val nextPart = MapManager.instance.objectManager.routeRepository.getPartById(nextPartId)!!
        return PartState(nextPart)
    }

    override fun moveToInactive(): IRouteInspectorState {
        return InactiveState()
    }
}