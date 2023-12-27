package com.example.historicalpetersburg.map.main.routeinspector

import com.example.historicalpetersburg.map.main.objects.PartRoute
import com.example.historicalpetersburg.map.main.objects.RouteData

class PathState(
    override val partRoute: PartRoute,
    val routeId: Int
) : IRouteInspectorState {
    override fun moveToStart(route: RouteData): IRouteInspectorState {
        return moveToInactive()
    }

    override fun moveToNextPart(): IRouteInspectorState {
        return PartState(partRoute, routeId)
    }

    override fun moveToInactive(): IRouteInspectorState {
        return InactiveState()
    }

    override fun accept(visitor: IRouteInspectorVisitor) {
        visitor.visit(this)
    }
}