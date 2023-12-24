package com.example.historicalpetersburg.map.main.routeinspector

import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.main.objects.RouteData


class InactiveState : IRouteInspectorState {
    override val partRoute = null

    override fun moveToStart(route: RouteData): IRouteInspectorState {
        val firstPart = MapManager.instance.objectManager.routeRepository.getFirstPartByRoute(route)!!
        // TODO проверяем, где мы, задаем отслеживание


        return PartState(firstPart)
    }

    override fun moveToNextPart(): IRouteInspectorState {
        return this
    }

    override fun moveToInactive(): IRouteInspectorState {
        return this
    }

    override fun accept(visitor: IRouteInspectorVisitor) {
        visitor.visit(this)
    }
}