package com.example.historicalpetersburg.map.main.routeinspector

import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.main.objects.PartRoute
import com.example.historicalpetersburg.map.main.objects.RouteData

class PartState(
    override val partRoute: PartRoute,
    val routeId: Int
) : IRouteInspectorState {

    override fun moveToStart(route: RouteData): IRouteInspectorState {
        return moveToInactive()
    }

    override fun moveToNextPart(): IRouteInspectorState {
        MapManager.instance.userManager.repository.saveCompletePart(partRoute, routeId)

        val nextPartId = partRoute.nextId
        if (nextPartId == null) {
            return InactiveState()
        }

        val nextPart = MapManager.instance.objectManager.routeRepository.getPartById(nextPartId)!!
        if (nextPart.preview == null) {
            return PartState(nextPart, routeId)
        }

        return PathState(nextPart, routeId)
    }

    override fun moveToInactive(): IRouteInspectorState {
        MapManager.instance.userManager.repository.saveCompletePart(partRoute, routeId)
        return InactiveState()
    }

    override fun accept(visitor: IRouteInspectorVisitor) {
        visitor.visit(this)
    }
}