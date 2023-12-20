package com.example.historicalpetersburg.map.services.route

import com.example.historicalpetersburg.map.main.objects.RouteData


class InactiveState : IRouteInspectorState {
    override fun moveToStart(route: RouteData): IRouteInspectorState {
        TODO("Not yet implemented")
    }

    override fun moveToPlace(): IRouteInspectorState {
        TODO("Not yet implemented")
    }

    override fun moveToPath(): IRouteInspectorState {
        TODO("Not yet implemented")
    }

    override fun moveToInactive(): IRouteInspectorState {
        TODO("Not yet implemented")
    }
}