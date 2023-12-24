package com.example.historicalpetersburg.map.main.routeinspector

import com.example.historicalpetersburg.map.main.objects.PartRoute
import com.example.historicalpetersburg.map.main.objects.RouteData


interface IRouteInspectorState {
    val partRoute: PartRoute?

    fun moveToStart(route: RouteData): IRouteInspectorState
    fun moveToNextPart(): IRouteInspectorState
    fun moveToInactive(): IRouteInspectorState

    fun accept(visitor: IRouteInspectorVisitor)
}