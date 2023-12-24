package com.example.historicalpetersburg.map.main.routeinspector

import com.example.historicalpetersburg.map.main.objects.PartRoute
import com.example.historicalpetersburg.map.main.objects.RouteData

class PathState(
    override val partRoute: PartRoute
) : IRouteInspectorState {
    override fun moveToStart(route: RouteData): IRouteInspectorState {
        return moveToInactive()
    }

    override fun moveToNextPart(): IRouteInspectorState {
        // TODO("проверка пришли ли?")
        return PartState(partRoute)
    }

    override fun moveToInactive(): IRouteInspectorState {
        return InactiveState()
    }

    override fun accept(visitor: IRouteInspectorVisitor) {
        visitor.visit(this)
    }
}