package com.example.historicalpetersburg.map.services.route

import com.example.historicalpetersburg.map.main.objects.RouteData


class RouteInspector {

    private var state: IRouteInspectorState = InactiveState()

    val isActive: Boolean
        get() = state !is InactiveState

    fun start(route: RouteData) {
        state = state.moveToStart(route)
    }

    fun stop() {
        state = state.moveToInactive()
    }
}