package com.example.historicalpetersburg.map.services.route

import com.example.historicalpetersburg.map.entities.Route

interface IRouteInspectorState {
    fun moveToStart(route: Route): IRouteInspectorState
    fun moveToPlace(): IRouteInspectorState
    fun moveToPath(): IRouteInspectorState
    fun moveToInactive(): IRouteInspectorState
}