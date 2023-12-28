package com.example.historicalpetersburg.map.main.routeinspector

import com.example.historicalpetersburg.map.main.objects.PartRoute
import com.example.historicalpetersburg.map.main.objects.RouteData


class RouteInspector {

    private var state: IRouteInspectorState = InactiveState()
        set(value) {
            field = value
            visitor?.let { field.accept(it) }
        }

    var visitor: IRouteInspectorVisitor? = null

    val isActive: Boolean
        get() = state !is InactiveState

    val curPart: PartRoute?
        get() = state.partRoute

    fun start(route: RouteData) {
        state = state.moveToStart(route)
    }

    fun toNextPart() {
        state = state.moveToNextPart()
    }

    fun stop() {
        state = state.moveToInactive()
    }
}