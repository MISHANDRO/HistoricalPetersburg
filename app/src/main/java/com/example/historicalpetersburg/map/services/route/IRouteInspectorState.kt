package com.example.historicalpetersburg.map.services.route

import com.example.historicalpetersburg.map.main.objects.Route


interface IRouteInspectorState {
    fun moveToStart(route: Route): IRouteInspectorState
    fun moveToPlace(): IRouteInspectorState
    fun moveToPath(): IRouteInspectorState
    fun moveToInactive(): IRouteInspectorState
}