package com.example.historicalpetersburg.map.main.filters

import com.example.historicalpetersburg.map.main.objects.IHistoricalObjectData

abstract class HistoricalObjectFilterChainBase  : IHistoricalObjectFilterChain {

    private var _next: IHistoricalObjectFilterChain? = null

    override fun addNext(next: IHistoricalObjectFilterChain) {
        if (_next == null) {
            _next = next
        } else {
            _next!!.addNext(next)
        }
    }

    override fun isNormal(historicalObject: IHistoricalObjectData): Boolean {
        if (Handle(historicalObject)) {
            return if (_next == null) {
                true
            } else {
                _next!!.isNormal(historicalObject)
            }
        }

        return false
    }

    abstract fun Handle(historicalObject: IHistoricalObjectData): Boolean
}