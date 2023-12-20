package com.example.historicalpetersburg.map.services.route

import com.example.historicalpetersburg.map.main.objects.RouteData


interface IRouteInspectorState {
    fun moveToStart(route: RouteData): IRouteInspectorState
    fun moveToPlace(): IRouteInspectorState
    fun moveToPath(): IRouteInspectorState
    fun moveToInactive(): IRouteInspectorState
}