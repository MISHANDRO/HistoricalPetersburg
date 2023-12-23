package com.example.historicalpetersburg.map.services.route

import com.example.historicalpetersburg.map.main.objects.RouteData


interface IRouteInspectorState {
    fun moveToStart(route: RouteData): IRouteInspectorState
    fun moveToNextPart(partRouteId: Int): IRouteInspectorState
    fun moveToInactive(): IRouteInspectorState
}