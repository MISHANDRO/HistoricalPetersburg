package com.example.historicalpetersburg.map.main.routeinspector

interface IRouteInspectorVisitor {
    fun visit(state: InactiveState)
    fun visit(state: PathState)
    fun visit(state: PartState)
}